package com.project.bitereg.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseModel(open var id: String? = null) : Parcelable
