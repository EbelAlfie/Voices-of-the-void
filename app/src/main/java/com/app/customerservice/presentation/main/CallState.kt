package com.app.customerservice.presentation.main

import io.getstream.video.android.core.Call

sealed class CallState {

  data class CallingAI(val responseMsg: String = ""): CallState()

  data class CallingCustomerService(val call: Call): CallState()

  data class Error(val error: Throwable?): CallState()

}