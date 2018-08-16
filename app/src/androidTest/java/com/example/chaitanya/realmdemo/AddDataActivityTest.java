package com.example.chaitanya.realmdemo;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.example.chaitanya.realmdemo.Activity.AddDataActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.regex.Matcher;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withAlpha;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 11/8/18,12:17 PM.
 * For : ISS 24/7, Pune.
 */
@RunWith(AndroidJUnit4.class)
public class AddDataActivityTest {

    private static final String NAME = "Ravindra";
    private static final String AGE = "26";
    private static final String MOBILE = "0123456789";
    private static final String EMAIL = "rrr@gmail.com";
    private static final String DOB = "01-06-1992";

    @Rule
    public ActivityTestRule<AddDataActivity> activityTestRule = new ActivityTestRule<>(AddDataActivity.class);

    @Test
    public void formValidation() {

        Espresso.onView((withId(R.id.edtName))).perform(typeText(NAME), closeSoftKeyboard());
        Espresso.onView((withId(R.id.edtAge))).perform(typeText(AGE), closeSoftKeyboard());
        Espresso.onView((withId(R.id.edtMobile))).perform(typeText(MOBILE), closeSoftKeyboard());
        Espresso.onView((withId(R.id.edtEmail))).perform(typeText(EMAIL), closeSoftKeyboard());

//        Espresso.onView((withId(R.id.edtDOB))).perform(typeText(DOB), closeSoftKeyboard());

        Espresso.onView((withId(R.id.imgbtnDate))).perform(click());
        Espresso.pressBack();

//        Espresso.onData(allOf(is(instanceOf(Calendar.class)), is("01-06-1992"))).perform(click());
//        Espresso.onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(ViewAction.setDate(year, monthOfYear, dayOfMonth));

//        Espresso.onView((withId(R.id.edtDOB))).perform(typeText(DOB));

//        Espresso.onData(allOf(is(instanceOf(TimePicker.class)))).perform(click());

        Espresso.onView((withId(R.id.spBloodGroup))).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("B+"))).perform(click());

        Espresso.onView(withId(R.id.ckbReading)).check(matches(isNotChecked())).perform(scrollTo(), click());
        Espresso.onView(withId(R.id.ckbWriting)).check(matches(isNotChecked())).perform(scrollTo(), click());
        Espresso.onView(withId(R.id.ckbDrawing)).check(matches(isNotChecked())).perform(scrollTo(), click());

        Espresso.onView(withId(R.id.rdbInActive)).check(matches(isNotChecked())).perform(scrollTo(), click());

        Espresso.onView((withId(R.id.btnSubmit))).perform(click());

        Espresso.onView((withId(R.id.btnView))).perform(click());

        Espresso.onView(withText("Test")).inRoot(isDialog()) .check(matches(isDisplayed()));

    }

}
