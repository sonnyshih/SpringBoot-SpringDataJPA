package com.example.serveletDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet(
        name = "ShowImageVideoServlet",
        description = "1. Get mp4, m3u8, JPG stream. 2. When not use the type, can download file.",
        urlPatterns = {"/showImageVideoServlet"}
)
public class showImageVideoServlet extends HttpServlet {

    @Value("${eip.docPath}")
    String docPath;
    private static final int DEFAULT_BUFFER_SIZE = 20480; // ..bytes = 20KB.
    private static final String MULTIPART_BOUNDARY = "MULTIPART_BYTERANGES";

    public final static int CONTENT_TYPE_MP4 = 1;
    public final static int CONTENT_TYPE_M3U8 = 2;
    public final static int CONTENT_TYPE_JPG = 3;

    private Map<Integer, String> CONTENT_TYPE_MAP = new HashMap<Integer, String>(){
        {
            put(CONTENT_TYPE_MP4, "video/mp4");
            put(CONTENT_TYPE_M3U8, "application/x-mpegURL");
            put(CONTENT_TYPE_JPG, "image/jpeg");
        }
    };


    private static final Logger logger = LoggerFactory.getLogger(showImageVideoServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

    /**
     * 1. type : 顯示影片的格式是何種
     * 2. filePath : 檔案完整路徑. 例: /eip2-trainingVideo/28/xxx.mp4
     * 3. View video (mp4) Ex: http://localhost:8080/showImageVideoServlet?type=1&filePath=/video/ocean.mp4
     * 4. View video (m3u8) Ex: http://localhost:8080/showImageVideoServlet?type=2&filePath=/video/30/test.m3u8
     * 5. View photo Ex: http://localhost:8080/showImageVideoServlet?type=3&filePath=/images/01.jpg
     * 6. Download file Ex: http://localhost:8080/showImageVideoServlet?filePath=/video/ocean.mp4
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int type = request.getParameter("type")!=null? Integer.parseInt(request.getParameter("type")) : 0;
        String filePath = request.getParameter("filePath");

        filePath = docPath + filePath;

        logger.info("File Path = " + filePath);

        File file = new File(filePath);
        Long length = file.length();
        String fileName = file.getName();

        if (!file.exists()) {
            showError(response, "File is not exist");
            logger.error("Show Online File Error : filePath is not exist." );
            return;
        }

        // Validate and process range -------------------------------------------------------------

        // Prepare some variables. The full Range represents the complete file.
        Range full = new Range(0, length - 1, length);
        List<Range> ranges = new ArrayList<>();

        // Validate and process Range and If-Range headers.
        String range = request.getHeader("Range");
        if (range != null) {

            // Range header should match format "bytes=n-n,n-n,n-n...". If not, then return 416.
            if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
                response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
                response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }

            String ifRange = request.getHeader("If-Range");
            if (ifRange != null && !ifRange.equals(fileName)) {
                try {
                    long ifRangeTime = request.getDateHeader("If-Range"); // Throws IAE if invalid.
                    if (ifRangeTime != -1) {
                        ranges.add(full);
                    }
                } catch (IllegalArgumentException ignore) {
                    ranges.add(full);
                }
            }

            // If any valid If-Range header, then process each part of byte range.
            if (ranges.isEmpty()) {
                for (String part : range.substring(6).split(",")) {
                    // Assuming a file with length of 100, the following examples returns bytes at:
                    // 50-80 (50 to 80), 40- (40 to length=100), -20 (length-20=80 to length=100).
                    long start = Range.sublong(part, 0, part.indexOf("-"));
                    long end = Range.sublong(part, part.indexOf("-") + 1, part.length());

                    if (start == -1) {
                        start = length - end;
                        end = length - 1;
                    } else if (end == -1 || end > length - 1) {
                        end = length - 1;
                    }

                    // Check if Range is syntactically valid. If not, then return 416.
                    if (start > end) {
                        response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
                        response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                        return;
                    }

                    // Add range.
                    ranges.add(new Range(start, end, length));
                }
            }
        }

        // Prepare and initialize response --------------------------------------------------------

        // Get content type by file name and set content disposition.
        String disposition = "inline";

        // If content type is unknown, then set the default value.
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        // To add new content types, add new mime-mapping entry in web.xml.
        String contentType = "application/octet-stream";
        switch (type) {
            case CONTENT_TYPE_MP4:
                contentType = CONTENT_TYPE_MAP.get(CONTENT_TYPE_MP4);
                break;

            case CONTENT_TYPE_M3U8:
                contentType = CONTENT_TYPE_MAP.get(CONTENT_TYPE_M3U8);
                break;

            case CONTENT_TYPE_JPG:
                contentType = CONTENT_TYPE_MAP.get(CONTENT_TYPE_JPG);
                break;

            default:
                String accept = request.getHeader("Accept");
                disposition = accept != null && HttpUtils.accepts(accept, contentType) ? "inline" : "attachment";
                break;
        }

        logger.info("Content Type==" + contentType);

        // Initialize response.
        response.reset();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Disposition", disposition + ";filename=\"" + fileName + "\"");
        response.setHeader("Accept-Ranges", "bytes");

        InputStream input = null;

        // Send requested file (part(s)) to client ------------------------------------------------
        // Prepare streams.
        try {

            input = new BufferedInputStream(new FileInputStream(file));
            ServletOutputStream output = response.getOutputStream();

            if (ranges.isEmpty() || ranges.get(0) == full) {

                // Return full file.
//                logger.info("Return full file");
                response.setContentType(contentType);
                response.setHeader("Content-Range", "bytes " + full.start + "-" + full.end + "/" + full.total);
                response.setHeader("Content-Length", String.valueOf(full.length));
                Range.copy(input, output, length, full.start, full.length);

            } else if (ranges.size() == 1) {

                // Return single part of file.
                Range r = ranges.get(0);
//                logger.info("Return 1 part of file : from ({}) to ({})", r.start, r.end);
                response.setContentType(contentType);
                response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
                response.setHeader("Content-Length", String.valueOf(r.length));
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

                // Copy single part range.
                Range.copy(input, output, length, r.start, r.length);

            } else {

                // Return multiple parts of file.
                response.setContentType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY);
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

                // Cast back to ServletOutputStream to get the easy println methods.
                ServletOutputStream sos = (ServletOutputStream) output;

                // Copy multi part range.
                for (Range r : ranges) {
//                    logger.info("Return multi part of file : from ({}) to ({})", r.start, r.end);
                    // Add multipart boundary and header fields for every range.
                    sos.println();
                    sos.println("--" + MULTIPART_BOUNDARY);
                    sos.println("Content-Type: " + contentType);
                    sos.println("Content-Range: bytes " + r.start + "-" + r.end + "/" + r.total);

                    // Copy single part range of multi part range.
                    Range.copy(input, output, length, r.start, r.length);
                }

                // End with multipart boundary.
                sos.println();
                sos.println("--" + MULTIPART_BOUNDARY + "--");
            }
        } catch (IOException e) {
            logger.error("Show online File Error (IOException): " + e.toString());
        }

    }

    private void showError(HttpServletResponse response, String msg) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>無法顯示檔案-錯誤訊息</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1> Error Message: " + msg + "</h1>");
        out.println("</body>");
        out.println("</html>");

        logger.error("Show online File Error: " + msg);
    }

    private static class Range {
        long start;
        long end;
        long length;
        long total;

        /**
         * Construct a byte range.
         * @param start Start of the byte range.
         * @param end End of the byte range.
         * @param total Total length of the byte source.
         */
        public Range(long start, long end, long total) {
            this.start = start;
            this.end = end;
            this.length = end - start + 1;
            this.total = total;
        }

        public static long sublong(String value, int beginIndex, int endIndex) {
            String substring = value.substring(beginIndex, endIndex);
            return (substring.length() > 0) ? Long.parseLong(substring) : -1;
        }

        private static void copy(InputStream input, OutputStream output, long inputSize, long start, long length) throws IOException {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int read;

            if (inputSize == length) {
                // Write full range.
                while ((read = input.read(buffer)) > 0) {
                    output.write(buffer, 0, read);
                    output.flush();
                }
            } else {
                input.skip(start);
                long toRead = length;

                while ((read = input.read(buffer)) > 0) {
                    if ((toRead -= read) > 0) {
                        output.write(buffer, 0, read);
                        output.flush();
                    } else {
                        output.write(buffer, 0, (int) toRead + read);
                        output.flush();
                        break;
                    }
                }
            }
        }
    }

    private static class HttpUtils {

        /**
         * Returns true if the given accept header accepts the given value.
         * @param acceptHeader The accept header.
         * @param toAccept The value to be accepted.
         * @return True if the given accept header accepts the given value.
         */
        public static boolean accepts(String acceptHeader, String toAccept) {
            String[] acceptValues = acceptHeader.split("\\s*(,|;)\\s*");
            Arrays.sort(acceptValues);

            return Arrays.binarySearch(acceptValues, toAccept) > -1
                    || Arrays.binarySearch(acceptValues, toAccept.replaceAll("/.*$", "/*")) > -1
                    || Arrays.binarySearch(acceptValues, "*/*") > -1;
        }

        /**
         * Returns true if the given match header matches the given value.
         * @param matchHeader The match header.
         * @param toMatch The value to be matched.
         * @return True if the given match header matches the given value.
         */
        public static boolean matches(String matchHeader, String toMatch) {
            String[] matchValues = matchHeader.split("\\s*,\\s*");
            Arrays.sort(matchValues);
            return Arrays.binarySearch(matchValues, toMatch) > -1
                    || Arrays.binarySearch(matchValues, "*") > -1;
        }
    }
}
