package com.example.ilovezappos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class Interval extends Fragment {

    Button submit;
    Button cancel;
    EditText interval;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.interval, null);
        submit = v.findViewById(R.id.submit);
        interval = v.findViewById(R.id.interval);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileOutputStream fileOutputStream;
                try {
                    fileOutputStream = getContext().openFileOutput("price.txt",getContext().MODE_PRIVATE);
                    System.out.println(interval.getText().toString());
                    fileOutputStream.write(interval.getText().toString().getBytes());
                    fileOutputStream.close();
                    final WorkManager mWorkManager = WorkManager.getInstance();
                    final PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class,1, TimeUnit.HOURS)
                            .build();
                    mWorkManager.cancelAllWork();
                    mWorkManager.enqueue(mRequest);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getContext(), "Amount Set", Toast.LENGTH_LONG).show();




            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });






        return v;




    }
}
