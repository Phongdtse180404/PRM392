package com.example.chair_shop.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mText2;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        mText2 = new MutableLiveData<>(); // Khởi tạo
        mText2.setValue("Tôi là số 1");   // Đặt giá trị
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getText2() {return mText2; }
}