package edict.common.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

    private TextView vTime;

    public void setTimeTextView(TextView vTime) {
        this.vTime = vTime;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                false);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            
        StringBuilder sb = new StringBuilder("");

        String suffix = hourOfDay < 12 ? "AM" : "PM";
        hourOfDay = hourOfDay % 12;
        String hourS = hourOfDay<10 ? "0"+hourOfDay : "" +hourOfDay;
        String minuteS = minute<10 ? "0"+minute : "" + minute;

        if(hourOfDay == 0) {
            hourS = "12";
        }

        sb.append(hourS);
        sb.append(":");
        sb.append(minuteS);
        sb.append(suffix);

        vTime.setText(sb);
        vTime.setTextColor(Color.parseColor("#204060"));
    }
}