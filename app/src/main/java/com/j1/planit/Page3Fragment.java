package com.j1.planit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PC on 2018-05-02.
 */

public class Page3Fragment extends Fragment {

    Button clickOk;
    Button clickCancel;
    EditText editTitle;
    Button pickDate;
    Button pickTime;
    Spinner colorSpinner;
    Spinner shareSpinner;
    Spinner setAlarm;
    EditText editDetails;

    Date currentDate;
    String title;
    String details;
    String time;
    String date;
    String color;
    String share;
    String alarm;



    ArrayAdapter colorArrayAdapter;
    ArrayAdapter shareArrayAdapter;
    ArrayAdapter alarmArrayAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3_fragment, container, false);

        clickOk = view.findViewById(R.id.add_btn_clickOk);
        clickCancel = view.findViewById(R.id.add_btn_clickCancel);
        editTitle = view.findViewById(R.id.add_title);
        pickDate = view.findViewById(R.id.add_btn_pickdate);
        pickTime = view.findViewById(R.id.add_btn_picktime);
        colorSpinner = view.findViewById(R.id.add_color_spinner);
        shareSpinner = view.findViewById(R.id.add_share_spinner);
        setAlarm = view.findViewById(R.id.add_set_alarm);
        editDetails = view.findViewById(R.id.add_details);


        colorArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.color_datas, R.layout.spinner_selected);
        colorArrayAdapter.setDropDownViewResource(R.layout.spinner_selected);
        colorSpinner.setAdapter(colorArrayAdapter);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                color = colorSpinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shareArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.share_datas, R.layout.spinner_selected);
        shareArrayAdapter.setDropDownViewResource(R.layout.spinner_selected);
        shareSpinner.setAdapter(shareArrayAdapter);
        shareSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                share = shareSpinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        alarmArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.alarm_datas, R.layout.spinner_selected);
        alarmArrayAdapter.setDropDownViewResource(R.layout.spinner_selected);
        setAlarm.setAdapter(alarmArrayAdapter);
        setAlarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                alarm = setAlarm.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        clickOk.setOnClickListener(clickListener);
        clickCancel.setOnClickListener(clickListener);
        pickTime.setOnClickListener(clickListener);
        pickDate.setOnClickListener(clickListener);




        return view;

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();


            if(id == R.id.add_btn_clickOk){

                title = editTitle.getText().toString();
                details = editDetails.getText().toString();
                date = date+"/"+time;

                if(title.length() == 0 || details.length()==0 || date == null || time == null){
                    return;
                }else{
                    //DB에 올림.


                    String serverUrl = "http://mysterlee418.dothome.co.kr/Planit/planitInsertDB.php";

                    SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("test200", response);
                            //insertDB.php의 echo결과 보여주기
//                            new android.support.v7.app.AlertDialog.Builder(getContext()).setMessage(response).setPositiveButton("OK", null).create().show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    String email = MyCalendarActivity.emailAddress;


                    multiPartRequest.addStringParam("email", email);
                    multiPartRequest.addStringParam("title", title);
                    multiPartRequest.addStringParam("date", date);
                    multiPartRequest.addStringParam("color", color);
                    multiPartRequest.addStringParam("share", share);
                    multiPartRequest.addStringParam("details", details);
                    multiPartRequest.addStringParam("alarm", alarm);

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(multiPartRequest);


                    editTitle.setText("");
                    editDetails.setText("");





                    MyCalendarActivity activity = (MyCalendarActivity) getActivity();
                    activity.viewPager.setCurrentItem(0);
                }






            }else if(id == R.id.add_btn_clickCancel){
                MyCalendarActivity activity = (MyCalendarActivity) getActivity();
                activity.viewPager.setCurrentItem(0);


            }else if(id == R.id.add_btn_pickdate){
                //date picker
                long now = System.currentTimeMillis();
                currentDate = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
                String formatDate = sdfNow.format(currentDate);
                String[] dateDatas = formatDate.split("/");
                DatePickerDialog dialog = new DatePickerDialog(getContext(), onDateSetListener, Integer.parseInt(dateDatas[0]), Integer.parseInt(dateDatas[1])-1, Integer.parseInt(dateDatas[2]));
                dialog.show();


            }else if(id == R.id.add_btn_picktime){
                //time picker
                long now = System.currentTimeMillis();
                currentDate = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("HH/mm/ss");
                String formatDate = sdfNow.format(currentDate);
                String[] timeDatas = formatDate.split("/");
                TimePickerDialog dialog = new TimePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, onTimeSetListener, Integer.parseInt(timeDatas[0]), Integer.parseInt(timeDatas[1]), false);
                dialog.show();

            }
        }
    };





    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {

            String ys=y+"";
            String ms;
            String ds;


            if(m<10){
                ms = "0"+(m+1);
            }else{
                ms = ""+(m+1);
            }

            if(d<10){
                ds = "0"+d;
            }else{
                ds = ""+d;
            }

            date = ys+"/"+ms+"/"+ds;

            pickDate.setText(date);
            pickDate.setTextColor(Color.BLACK);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int h, int m) {
            String hs;
            String ms;

            if(h<10){
                hs = "0"+h;
            }else {
                hs = ""+h;
            }

            if(m<10){
                ms = "0"+m;
            }else{
                ms =""+m;
            }

            time = hs+":"+ms;

            pickTime.setText(time);
            pickTime.setTextColor(Color.BLACK);

        }
    };



}


