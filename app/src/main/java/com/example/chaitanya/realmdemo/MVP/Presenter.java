package com.example.chaitanya.realmdemo.MVP;

import android.util.Log;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 13/8/18,5:35 PM.
 * For : ISS 24/7, Pune.
 */
public class Presenter  {

    private Model model;
    private View view;

    public Presenter(View view) {
        this.model = new Model();
        this.view = view;
    }

    public void updateName(String name){
        model.setName(name);
        view.updateName(model.getName());
    }

    public interface View{
        public void updateName(String name);
    }

}
