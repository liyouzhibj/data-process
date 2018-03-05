package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.bo.KeyPosition;
import com.liyouzhi.dataprocess.bo.NoteStatus;
import com.liyouzhi.dataprocess.service.DataProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataProcessImpl implements DataProcess<String, KeyPosition, String, String> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String annotationFilter(String note) {
        if(note == null || "".equals(note.trim()))
        {
            return null;
        }
        NoteStatus noteStatus =  NoteStatus.getInstance();

        StringBuffer a  = new StringBuffer("");


        String[] aaa = note.split("\"(.*?)\"");
        List<String> quats = new ArrayList<String>();
        Pattern p=Pattern.compile("\"(.*?)\"");
        Matcher m=p.matcher(note);
        while(m.find())
        {
            quats.add(m.group());
        }
        if(quats.size()>0)
        {
            int i = 0;
            int length = aaa.length-1;
            for (String temp : aaa) {
                if (noteStatus.isHasStart()) {
                    return "";
                }
                if (temp.indexOf("/*") >= 0 && temp.indexOf("*/") >= 0) {

                } else {
                    if (temp.indexOf("/*") >= 0) {
                        noteStatus.setHasStart(true);
                        a.append(temp.substring(0, temp.indexOf("/*")));
                        break;
                    }
                    if (temp.indexOf("*/") >= 0) {
                        noteStatus.setHasStart(false);
                        a.append(temp.substring(temp.indexOf("*/"), temp.length()));
                        break;
                    }
                }

                a.append(temp.replaceAll("(?<!:)\\/\\/.*|\\/\\*(\\s|.)*?\\*\\/", ""));

                if(i<length)
                {
                    a.append(quats.get(i));
                }
                i++;
            }
        }else
        {
            if (note.indexOf("/*") >= 0) {
                noteStatus.setHasStart(true);
                if(note.indexOf("/*") > 0)
                {
                    a.append(note.substring(0, note.indexOf("/*")));
                }else
                {
                    note="";
                }
            }
            if(noteStatus.isHasStart() && note.indexOf("*/") >= 0 )
            {
                if (note.indexOf("*/") >= 0) {
                    noteStatus.setHasStart(false);
                    if(note.endsWith("*/"))
                    {
                        a.append("");
                    }else {
                        a.append(note.substring(note.indexOf("*/"), note.length()));
                    }
                    return a.toString();
                }
            }else if(noteStatus.isHasStart())
            {
                return "";
            }

            a.append(note.replaceAll("(?<!:)\\/\\/.*|\\/\\*(\\s|.)*?\\*\\/", ""));
        }

        return a.toString();
    }

    @Override
    public List<KeyPosition> getKey(String sourceData, String regex) {
        if(null == sourceData){
            sourceData = "";
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sourceData);
        List<KeyPosition> list = new ArrayList<>();

        while(m.find()) {
            KeyPosition keyPosition = new KeyPosition();
            keyPosition.setStart(m.start());
            keyPosition.setEnd(m.end());

            String key = sourceData.substring(m.start(), m.end());
            keyPosition.setKey(key);
            list.add(keyPosition);
            logger.info("match word: " + key);
        }

        return list;
    }

    @Override
    public String translationKey(String key, String lang) {
        return null;
    }
}
