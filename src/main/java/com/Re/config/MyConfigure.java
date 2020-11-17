package com.Re.config;

import com.Re.annotation.MyComponentScan;
import com.Re.annotation.MyConfiguration;

@MyConfiguration
@MyComponentScan({"com.Re.dao","com.Re.service","com.Re.controller"})
public class MyConfigure {

}
