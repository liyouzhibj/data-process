package com.liyouzhi.dataprocess.service.impl;

import com.liyouzhi.dataprocess.bo.KeyPosition;
import com.liyouzhi.dataprocess.bo.NoteStatus;
import com.liyouzhi.dataprocess.service.DataProcess;
import com.liyouzhi.dataprocess.util.HttpRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataProcessImpl implements DataProcess<String, KeyPosition, String, String> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${youdao.app.key}")
    private String youdaoAppKey;

    @Value("${youdao.secret.key}")
    private String youdaoSecretKey;

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
    public String translationKey(String key, String sourceLang, String targetLang) {
        HttpRequest httpRequest = new HttpRequest();
        String keyTranslation = "";

        String appKey = youdaoAppKey;
		String query = key;
		String salt = String.valueOf(System.currentTimeMillis());
		String from = sourceLang;
		String to = targetLang;
		String sign = httpRequest.md5(appKey + query + salt + youdaoSecretKey);
		Map<String, String> params = new HashMap<>();
		params.put("q", query);
		params.put("from", from);
		params.put("to", to);
		params.put("sign", sign);
		params.put("salt", salt);
		params.put("appKey", appKey);
		try{
            String result = httpRequest.requestForHttp("http://openapi.youdao.com/api", params);
            JSONObject jsonObject = new JSONObject(result);
            keyTranslation = jsonObject.getString("translation");
            if(keyTranslation != null && !keyTranslation.equals("")){
                keyTranslation = keyTranslation.substring(2, keyTranslation.length()-2);
            }
            logger.info("source key: " + key + " translation to : " + keyTranslation);
        }catch (Exception e){
		    logger.error("translation error: ", e.toString());
        }

        return keyTranslation;
    }
}
