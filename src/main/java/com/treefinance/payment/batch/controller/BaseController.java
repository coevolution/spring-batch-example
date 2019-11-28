package com.treefinance.payment.batch.controller;

import com.datatrees.commons.basic.Answer;
import com.datatrees.commons.exception.RequestArgumentNotValidException;
import com.datatrees.commons.utils.DateUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author lxp
 * @date 2019/11/28 上午10:31
 * @Version 1.0
 */
public class BaseController {
    protected static final String PAGE_SIZE = "pageSize";
    protected static final String CURRENT_PAGE = "currentPage";
    protected static final String STATUS = "status";

    private static int MIN_CURR_PAGE = 0;
    private static int MAX_CURR_PAGE = 2000 * 10000;
    private static int DEFAULT_CURR_PAGE = 1;

    public Integer getInt(HttpServletRequest request, String key, Integer defaultValue) {

        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {
            try {
                return Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
            }
        }

        return defaultValue;

    }

    public Long getLong(HttpServletRequest request, String key, Long defaultValue) {
        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {
            try {
                return Long.parseLong(parameter);
            } catch (NumberFormatException e) {
            }
        }

        return defaultValue;

    }

    public Byte getByte(HttpServletRequest request, String key, Byte defaultValue) {
        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {
            try {
                return Byte.parseByte(parameter);
            } catch (NumberFormatException e) {
            }
        }

        return defaultValue;

    }

    public Date getDate(HttpServletRequest request, String key, Date defaultValue) {
        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {
            try {
                return DateUtil.parseDateTime(parameter);
            } catch (Exception e) {
            }
        }

        return defaultValue;

    }

    public Date getNormalDate(HttpServletRequest request, String key, Date defaultValue) {
        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {
            try {
                return DateUtil.parseDate(parameter);
            } catch (Exception e) {
            }
        }

        return defaultValue;

    }

    public Date getDate(HttpServletRequest request, String key, String format, Date defaultValue) {
        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {
            try {
                return DateUtil.parseDate(parameter, format);
            } catch (Exception e) {
            }
        }

        return defaultValue;

    }

    public String getString(HttpServletRequest request, String key, String defaultValue) {

        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {
            return parameter;
        }

        return defaultValue;

    }

    public Boolean getBoolean(HttpServletRequest request, String key, Boolean defaultValue) {

        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {
            return Boolean.valueOf(parameter);
        }

        return defaultValue;

    }

    public BigDecimal getBigDecimal(HttpServletRequest request, String key, BigDecimal defaultValue) {

        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {
            return BigDecimal.valueOf(Double.parseDouble(parameter));
        }

        return defaultValue;

    }

    public Integer[] getInts(HttpServletRequest request, String key, Integer[] defaultValue) {

        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {

            String[] split = parameter.split(",");

            Integer[] res = new Integer[split.length];

            for (int i = 0; i < split.length; i++) {
                res[i] = Integer.parseInt(split[i]);
            }

            return res;

        }

        return defaultValue;

    }

    public Long[] getLongs(HttpServletRequest request, String key, Long[] defaultValue) {

        String parameter = request.getParameter(key);

        if (!StringUtils.isEmpty(parameter)) {

            String[] split = parameter.split(",");

            Long[] res = new Long[split.length];

            for (int i = 0; i < split.length; i++) {
                res[i] = Long.parseLong(split[i]);
            }

            return res;

        }

        return defaultValue;

    }

    public <T> Answer<T> renderAnswerT(T result) {
        Answer<T> answer = new Answer<T>();
        answer.setCode(0);
        answer.setMsg("操作完成");
        answer.setResult(result);
        return answer;
    }

    public <T> Answer<T> renderErrorT(String msg) {
        Answer<T> answer = new Answer<T>();
        answer.setCode(1);
        answer.setMsg(msg);
        return answer;
    }

    public <T> Answer<T> renderErrorT(String msg, T result) {
        Answer<T> answer = new Answer<T>();
        answer.setCode(1);
        answer.setMsg(msg);
        answer.setResult(result);
        return answer;
    }

    public void validBindResult(BindingResult result) throws RequestArgumentNotValidException {
        if (result.hasErrors()) {
            throw new RequestArgumentNotValidException(result.getFieldError().getDefaultMessage());
        }
    }

    public void validBindResult(BindingResult... results) throws RequestArgumentNotValidException {
        if (results == null)
            return;

        for (BindingResult result : results) {
            if (result.hasErrors()) {
                throw new RequestArgumentNotValidException(result.getFieldError().getDefaultMessage());
            }
        }
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attr =
            (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest();
    }

    public <T> T getSessionAttribute(String attrName, Class<T> clazz) {
        Object o = getSession().getAttribute(attrName);
        if (null != o) {
            return (T) o;
        }
        return null;
    }

    public List<Integer> split(String source, String delimiter) {
        try {
            List<Integer> list = new LinkedList<>();

            String[] splitArray = source.split(delimiter);
            for (String item : splitArray) {
                int id = Integer.parseInt(item);
                if (!list.contains(id)) {
                    list.add(id);
                }
            }

            return list;
        } catch (Exception e) {
            throw new RequestArgumentNotValidException(String.format(
                "Argument not valid. Multiple parameter should be separated by a delimiter '%s'.)",
                delimiter));
        }
    }

    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }

    public static int calculaetCurrentPage(final int currentPage, final int pageSize) {

        int offset =
            (currentPage < MIN_CURR_PAGE || currentPage > MAX_CURR_PAGE ? DEFAULT_CURR_PAGE
                : currentPage - 1) * pageSize;
        return offset < MIN_CURR_PAGE || offset > MAX_CURR_PAGE ? DEFAULT_CURR_PAGE : offset;

    }

    public static RowBounds buildRowBounds(final int currentPage, final int pageSize) {
        return new RowBounds(calculaetCurrentPage(currentPage, pageSize), pageSize);
    }

    /**
     *
     * @param request
     * @param encoding utf-8, gbk
     * @return
     */
    public static String readReqStr(HttpServletRequest request, String encoding) throws IOException {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), encoding));
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return sb.toString();
    }
}
