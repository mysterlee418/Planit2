package com.j1.planit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


    class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;
        Context context;

        public EventDecorator(int color, Collection<CalendarDay> dates, Context context) {

            this.color = color;
            this.dates = new HashSet<>(dates);
            this.context = context;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
//            Log.i("test300",""+dates.contains(day));
//            Log.i("test300",            day.toString());
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(8, color));
        }


    }