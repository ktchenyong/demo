package com.jetshine.util;

import java.io.File;
import java.io.IOException;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.util.ClassUtils;

import org.springframework.web.multipart.MultipartFile;



/**
 * 文件上传工具类
 */
public class FileUploadUtils {

    private FileUploadUtils(){}

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 1024*1024*1024;

    private static String defaultBaseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();




    /**
     * 默认的文件名最大长度
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;
    /**
     * 默认文件类型jpg
     */
    public static final String IMAGE_JPG_EXTENSION = ".jpg";

    private static int counter = 0;

    public static void setDefaultBaseDir(String defaultBaseDir)
    {
        FileUploadUtils.defaultBaseDir =Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    public static String getDefaultBaseDir()
    {
        return defaultBaseDir;
    }

	/**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大
     * @throws IOException 比如读写文件出错时
     */
    public static final String upload(String baseDir, MultipartFile file)
            throws FileSizeLimitExceededException, IOException
    {
    	String fileName=file.getOriginalFilename();
    	// 获得文件后缀名称
    	String suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    	if(suffixName==null||suffixName.length()<=0) {
    		//如果没有后缀默认后缀
    		suffixName=FileUploadUtils.IMAGE_JPG_EXTENSION;
    	}


        assertAllowed(file);

        String new_fileName = encodingFilename(fileName, suffixName);

        File desc = getAbsoluteFile(baseDir, baseDir + new_fileName);
        file.transferTo(desc);
        return new_fileName;
    }

    private static final File getAbsoluteFile(String uploadDir, String filename) throws IOException
    {
        File desc = new File(File.separator + filename);

        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists())
        {
            desc.createNewFile();
        }
        return desc;
    }



    private static final String encodingFilename(String filename, String extension)
    {
        filename = filename.replace("_", " ");
        filename = new Md5Hash(filename + System.nanoTime() + counter++).toHex().toString() + extension;
        return filename;
    }


    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     */
    public static final void assertAllowed(MultipartFile file) throws FileSizeLimitExceededException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException("超过默认大小", size, DEFAULT_MAX_SIZE);
        }
    }

}
