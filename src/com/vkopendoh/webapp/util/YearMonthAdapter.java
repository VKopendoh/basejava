package com.vkopendoh.webapp.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;

public class YearMonthAdapter extends XmlAdapter<String,YearMonth> {
    @Override
    public YearMonth unmarshal(String str) throws Exception {
        return YearMonth.parse(str);
    }

    @Override
    public String marshal(YearMonth yearMonth) throws Exception {
        return yearMonth.toString();
    }
}
