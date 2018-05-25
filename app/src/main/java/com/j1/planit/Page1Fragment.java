package com.j1.planit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by PC on 2018-05-02.
 */

public class Page1Fragment extends Fragment {

    MaterialCalendarView materialCalendarView;
    OneDayDecorator oneDayDecorator;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.page1_fragment, container, false);

        materialCalendarView = view.findViewById(R.id.calendarView);
        oneDayDecorator = new OneDayDecorator();
        materialCalendarView.addDecorators(new SundayDecorator(), oneDayDecorator);


        return view;




    }

    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {}

        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }


    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.parseColor("#491674")));

        }

        /**
         * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
         */
        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }



}
