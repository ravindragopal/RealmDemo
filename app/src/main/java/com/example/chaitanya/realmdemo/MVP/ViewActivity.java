package com.example.chaitanya.realmdemo.MVP;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chaitanya.realmdemo.R;

public class ViewActivity extends AppCompatActivity implements Test.name {

    TextView textView;
    EditText editText;
    Button button;

    Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        presenter = new Presenter(ViewActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.updateName(editText.getText().toString());
            }
        });
    }

    @Override
    public void updateName(String name) {
        textView.setText(name);
    }
}
