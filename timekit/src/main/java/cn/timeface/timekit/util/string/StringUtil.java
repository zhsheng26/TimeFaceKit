package cn.timeface.timekit.util.string;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean isMobileNum(String num) {
        String numberPatt = "^((13[0-9])|(14[5,7])|(17[0-9])|(15[^4,\\D])|(18[[0-9],\\D]))\\d{8}$";
        Pattern p = Pattern.compile(numberPatt);
        Matcher m = p.matcher(num);
        return m.matches();
    }

    public static String getPercentString(float percent) {
        return String.format(Locale.US, "%d%%", (int) (percent * 100));
    }

    /**
     * 删除字符串中的空白符
     *
     * @param content
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder buff = new StringBuilder();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
                    || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

    /**
     * 获取32位uuid
     *
     * @return
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isEmpty(String input) {
        return TextUtils.isEmpty(input);
    }

    /**
     * 生成唯一号
     *
     * @return
     */
    public static String get36UUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String makeMd5(String source) {
        return MD5.getStringMD5(source);
    }

    public static final String filterUCS4(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        if (str.codePointCount(0, str.length()) == str.length()) {
            return str;
        }

        StringBuilder sb = new StringBuilder();

        int index = 0;
        while (index < str.length()) {
            int codePoint = str.codePointAt(index);
            index += Character.charCount(codePoint);
            if (Character.isSupplementaryCodePoint(codePoint)) {
                continue;
            }

            sb.appendCodePoint(codePoint);
        }

        return sb.toString();
    }

    /**
     * counter ASCII character as one, otherwise two
     *
     * @param str
     * @return count
     */
    public static int counterChars(String str) {
        // return
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            int tmp = (int) str.charAt(i);
            if (tmp > 0 && tmp < 127) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    public static long getStandardDate(long date) {
        int length = String.valueOf(date).length();
        if (length == 13) {
            return date;
        } else if (length == 10) {
            return date * 1000;
        }
        return date;
    }

    public static <T> T jsonTobean(String result, Class<T> clazz) {
        return new Gson().fromJson(result, clazz);
    }

    public static String beanToJson(Object object) {
        return new Gson().toJson(object);
    }


    public static <T> ArrayList<T> jsonToList(String result, Class<T> clazz) {
        ArrayList<T> mList = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonReader reader = new JsonReader(new StringReader(result.trim()));
        reader.setLenient(true);
        JsonArray array = jsonParser.parse(reader).getAsJsonArray();
        Gson gson = new Gson();
        for (final JsonElement elem : array) {
            mList.add(gson.fromJson(elem, clazz));
        }
        return mList;
    }
}
