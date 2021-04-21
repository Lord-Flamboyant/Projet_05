package com.cleanup.todoc;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    //----- for data -----//
    TaskDatabase database;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initDb() throws Exception
    {
        this.database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), TaskDatabase.class)
                .allowMainThreadQueries().build();
    }

    @After
    public void closeDb() throws Exception
    {
        this.database.close();
    }

    @Test
    public void addAndRemoveTask() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        //----- delete task -----//
        RecyclerView listTasks1 = activity.findViewById(R.id.list_tasks);
        int countList = listTasks1.getAdapter().getItemCount();
        for (int i = 0 ; i < countList; i++){
            onView(withIndex(withId(R.id.img_delete), 0)).perform(click());
        }

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        // Check that lblTask is not displayed anymore
        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        // Check that recyclerView is displayed
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        // Check that it contains one element only
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(1));

        onView(withId(R.id.img_delete)).perform(click());

        // Check that lblTask is displayed
        assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
        // Check that recyclerView is not displayed anymore
        assertThat(listTasks.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void sortTasksByProject() {
        MainActivity activity = rule.getActivity();

        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        int countList = listTasks.getAdapter().getItemCount();
        for (int i = 0 ; i < countList; i++){
            onView(withIndex(withId(R.id.img_delete), 0)).perform(click());
        }


        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("wash the desk"));
        ViewInteraction appCompatSpinner1 = onView(allOf(withId(R.id.project_spinner),
                childAtPosition(allOf(withId(R.id.dialog_add),
                        childAtPosition(withId(R.id.custom), 0)), 1),
                isDisplayed()));
        appCompatSpinner1.perform(click());
        onData(anything()).inRoot(RootMatchers.isPlatformPopup()).atPosition(2).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("open windows"));
        ViewInteraction appCompatSpinner2 = onView(allOf(withId(R.id.project_spinner),
                childAtPosition(allOf(withId(R.id.dialog_add),
                        childAtPosition(withId(R.id.custom), 0)), 1),
                isDisplayed()));
        appCompatSpinner2.perform(click());
        onData(anything()).inRoot(RootMatchers.isPlatformPopup()).atPosition(1).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("empty the trash"));
        ViewInteraction appCompatSpinner3 = onView(allOf(withId(R.id.project_spinner),
                childAtPosition(allOf(withId(R.id.dialog_add),
                        childAtPosition(withId(R.id.custom), 0)), 1),
                isDisplayed()));
        appCompatSpinner3.perform(click());
        onData(anything()).inRoot(RootMatchers.isPlatformPopup()).atPosition(0).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("wash the desk")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("open windows")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("empty the trash")));

        // Sort alphabetical
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("wash the desk")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("open windows")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("empty the trash")));

        // Sort alphabetical inverted
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("empty the trash")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("open windows")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("wash the desk")));

        // Sort old first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("wash the desk")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("open windows")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("empty the trash")));


        //----- delete task -----//
        RecyclerView listTasks2 = activity.findViewById(R.id.list_tasks);
        int countList2 = listTasks2.getAdapter().getItemCount();
        for (int i = 0 ; i < countList2; i++){
            onView(withIndex(withId(R.id.img_delete), 0)).perform(click());
        }

    }

    //----- Option -----//

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


}
