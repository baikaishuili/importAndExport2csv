import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by cdliwenke on 2017/8/5.
 */
public class FileExportUtils {
    private static final Logger log = LoggerFactory.getLogger(FileExportUtils.class);
    public static final int QUERY_SIZE = 1000;
    private static final String CSV_CONTENT_TYPE = "application/octet-stream; charset=utf-8";

    public static void exportCsv(HttpServletResponse response, ExportConfig config, IExportDataQuery exportDataQuery,Object queryParam) {

        try {
            initResponse(response, CSV_CONTENT_TYPE, config.fileName);
            OutputStream os = response.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "gb2312"); //添加编码，解决中文乱码问题
            BufferedWriter bw = new BufferedWriter(osw);
            try {
                int restSize = config.size;
                int from = config.from;
                while (restSize > 0) {
                    int querySize = restSize > QUERY_SIZE ? QUERY_SIZE : restSize;
                    List<Object> result = exportDataQuery.queryExportData(queryParam,from, querySize);
                    result.forEach(object -> {
                        String s = getRowString(config,object);
                        try {
                            bw.append(s).append("\r");
                        } catch (IOException e) {
                            log.error("append error {}",s,e);
                        }
                    });
                    from = from + querySize;
                    restSize = restSize - querySize;
                }
            }catch (Exception e){

            }finally {
                closeQuiet(bw);
                closeQuiet(osw);
                closeQuiet(os);
            }
        } catch (Exception e) {

        }
    }

    private static String getRowString(ExportConfig config,Object data) {
        List<String> fieldList = config.getFieldName();
        StringBuffer rowSb = new StringBuffer();
        fieldList.forEach(fieldName -> {
            Field field = null;
            try {
                field = data.getClass().getField(fieldName);
                field.setAccessible(true);
                Object val = field.get(data);
                rowSb.append(val).append(",");
            } catch (Exception e) {
                log.error("反射获取field 值异常，object：{}", data, e);
            }
        });
        rowSb.deleteCharAt(rowSb.length()-1);
        return rowSb.toString();
    }


    /** 静默关闭对象 **/
    public static void closeQuiet(Closeable instance) {
        try {
            close(instance);
        } catch (Exception e) {}
    }

    /** 关闭对象 **/
    public static void close(Closeable instance) {
        if (instance != null) {
            try {
                instance.close();
            } catch (Exception e) {
                log.error("数据流关闭异常！", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 文件导出初始化响应头
     * @param response 请求响应
     * @param contentType 导出类型
     * @param filename 导出文件名
     */
    private static void initResponse(HttpServletResponse response, String contentType, String filename) {
        response.setContentType(contentType);
        response.addHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
    }
}
