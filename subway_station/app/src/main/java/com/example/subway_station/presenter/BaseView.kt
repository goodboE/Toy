package com.example.subway_station.presenter

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT
}