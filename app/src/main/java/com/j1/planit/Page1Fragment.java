package com.j1.planit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.misc.AsyncTask;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;


/**
 * Created by PC on 2018-05-02.
 */

public class Page1Fragment extends Fragment {

    MaterialCalendarView materialCalendarView;
    OneDayDecorator oneDayDecorator;
    ArrayList<MyCalendarPlans> myPlans = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        String serverUrl = "http://mysterlee418.dothome.co.kr/Planit/planitGetMCPlans.php";

        SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String[] rows= response.split(";");


                for(String row : rows){
                    String[] datas= row.split("&");
                    if(datas.length!=7) continue;

                    int no= Integer.parseInt(datas[0]);
                    String title= datas[1];
                    String date= datas[2];
                    String color= datas[3];
                    String share= datas[4];
                    String details= datas[5];
                    String alarm= datas[6];


                    Log.i("test200", no+"\n"+title+"\n"+date+"\n"+color+"\n"+share+"\n"+details+"\n"+alarm);
                    myPlans.add(new MyCalendarPlans(no,title,date,color,share,details,alarm));

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        String email = MyCalendarActivity.emailAddress;


        multiPartRequest.addStringParam("email", email);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(multiPartRequest);


        View view = inflater.inflate(R.layout.page1_fragment, container, false);

        materialCalendarView = view.findViewById(R.id.calendarView);
        oneDayDecorator = new OneDayDecorator();
        materialCalendarView.addDecorators(new SundayDecorator(), oneDayDecorator);




        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

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


        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }

    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] plans;


        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }


            ArrayList<CalendarDay> dates = new ArrayList<>();

            for (int i = 0; i < myPlans.size(); i++) {
                String date = myPlans.get(i).date;
                String[] strDates = date.split("/");
                int[] intDates = new int[3];

                for(int j=0;j<3;j++){
                    intDates[j] = Integer.parseInt(strDates[j]);
                }

                dates.add(CalendarDay.from(intDates[0], intDates[1]-1, intDates[2]));
                Log.i("test400", CalendarDay.from(intDates[0], intDates[1]-1, intDates[2]).toString());

            }
            materialCalendarView.setOnDateChangedListener(onDateSelectedListener);


            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);



            materialCalendarView.addDecorator(new EventDecorator(R.color.purple, calendarDays, getContext()));
        }
    }

    OnDateSelectedListener onDateSelectedListener = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            Toast.makeText(getContext(), date.toString(), Toast.LENGTH_SHORT).show();
        }
    };










}
