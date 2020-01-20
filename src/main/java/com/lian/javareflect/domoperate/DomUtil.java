package com.lian.javareflect.domoperate;


import com.lian.javareflect.service.UserService;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigInteger;
import java.util.ArrayList;


public class DomUtil {


    private static String fileDir = "E:\\reflect/";
    private static String errorWordFilePath = "liantengda.doc";
    private static String errorExcelFilePath = "liantengda.doc";

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        autoGenerateWordByAPI(fileDir,errorWordFilePath,"WORD");
    }

    /**
     * 自动生成文档类
     * @param fileDir
     * @param filePath
     * @param type
     */
    private static void autoGenerateWordByAPI(String fileDir, String filePath, String type) {
        Class clz = UserService.class;

        XWPFDocument document = new XWPFDocument();
        OutputStream stream = null;
        BufferedOutputStream bufferStream = null;
        try {
            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            stream = new FileOutputStream(new File(fileDir+filePath));
            bufferStream = new BufferedOutputStream(stream, 1024);

            String simpleName = clz.getSimpleName();
            //添加文章标题
            writeHeader(document,type,simpleName);
            Method[] methods = clz.getMethods();
            for (int i=0;i<methods.length;i++){
                String methodName = methods[i].getName();
                if(methodName.startsWith("get")||methodName.startsWith("sel")||methodName.startsWith("list")){
                    String  name = "查询用户基本信息";
                    //添加段落
                    writeParaTwith(document,methods[i],i,name,clz);
                }else if(methods[i].getName().startsWith("upd")){
                    String  name = "修改用户基本信息";
                    //添加段落
                    writeParaTwith(document,methods[i],i,name,clz);
                }else if(methods[i].getName().startsWith("del")){
                    String  name = "删除用户基本信息";
                    //添加段落
                    writeParaTwith(document,methods[i],i,name,clz);
                }else if(methods[i].getName().startsWith("add")){
                    String  name = "添加用户基本信息";
                    //添加段落
                    writeParaTwith(document,methods[i],i,name,clz);
                }
            }
            document.write(stream);
            stream.close();
            bufferStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("写word或Excel错误文件失败");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("写word或Excel错误文件失败");
                }
            }
            if (bufferStream != null) {
                try {
                    bufferStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("写word或Excel错误文件失败：{}");
                }
            }
        }
    }

    /**
     * 创建文章题目方法
     * @param document  dom节点树
     * @param type  文档后缀类型
     */
    public static void writeHeader(XWPFDocument document,String type,String simpleName){
        // 创建一个段落           文章题目
        XWPFParagraph p1 = document.createParagraph();
        // 设置居中
        p1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = p1.createRun();
        // 是否加粗
        r1.setBold(true);
        // 与下一行的距离
        r1.setTextPosition(30);
        if (type.equals("EXCEL")) {
            // 段落名称
            r1.setText(simpleName);
        }
        if (type.equals("WORD")) {
            // 段落名称
            r1.setText(simpleName);
        }
        // 字体大小
        r1.setFontSize(18);// 字体大小
        // 增加换行
        r1.addCarriageReturn();
    }

    /**
     * 第二标题段落书写
     * @param document
     * @param method
     */
    public static void writeParaTwith(XWPFDocument document, Method method,int index,String name,Class clz){

        // 创建第二个段落
        XWPFParagraph p2 = document.createParagraph();
        XWPFRun r2 = p2.createRun();
        // 是否加粗
        r2.setBold(true);
        r2.setText("1.1.1."+(index + 1)+"." + name);
        // 增加换行
        r2.addCarriageReturn();
        r2.setFontFamily("仿宋");
        r2.setFontSize(14);// 字体大小
        writeParaThirth(document,method,clz);
    }
    /**
     * 第三标题段落书写
     * @param document
     * @param method
     */
    public static void writeParaThirth(XWPFDocument document, Method method,Class clz){
        writeFunction(document,method,clz);
        writeService(document,method,clz);
        writeMethod(document,method,clz);
        writeProtocol(document,method,clz);
        writeInference(document,method,clz);
        writeInputParam(document,method,clz);
        writeOutputParam(document,method,clz);
        writeExaCod(document,method,clz);
    }

    /**
     * 功能说明
     * @param document
     * @param method
     * @param clz
     */
    public static void writeFunction(XWPFDocument document,Method method,Class clz){
        // 创建第三个段落
        XWPFParagraph p3 = document.createParagraph();
        //启动
        XWPFRun r3 = p3.createRun();
        // 是否加粗
        r3.setBold(true);
        //设置文本
        r3.setText("1)"+"功能说明");
        // 增加换行
        r3.addCarriageReturn();
        // 设置字体
        r3.setFontFamily("仿宋");
        r3.setFontSize(11);// 字体大小

        String function = "有什么功能";
        //写出第四段
        operatorText(document,function);
    }

    /**
     * 服务名称
     *
     * @param document
     * @param method
     * @param clz
     */
    public static void writeService(XWPFDocument document,Method method,Class clz){
        // 创建第三个段落
        XWPFParagraph p3 = document.createParagraph();
        //启动
        XWPFRun r3 = p3.createRun();
        // 是否加粗
        r3.setBold(true);

        r3.setText("2)"+"服务名称");
        // 增加换行
        r3.addCarriageReturn();
        // 设置字体
        r3.setFontFamily("仿宋");
        r3.setFontSize(11);// 字体大小

        //服务内容
        String service = clz.getPackage().getName();
        //写出第四段
        operatorText(document,service);
    }

    /**
     * 方法名称
     * @param document
     * @param method
     * @param clz
     */
    public static void writeMethod(XWPFDocument document,Method method,Class clz){
        // 创建第三个段落
        XWPFParagraph p3 = document.createParagraph();
        //启动
        XWPFRun r3 = p3.createRun();
        // 是否加粗
        r3.setBold(true);
        //设置文本
        r3.setText("3)"+"方法名称");
        // 增加换行
        r3.addCarriageReturn();
        // 设置字体
        r3.setFontFamily("仿宋");
        r3.setFontSize(11);// 字体大小
        StringBuilder sb = new StringBuilder();
        Parameter[] parameters = method.getParameters();
        for(int i=0;i<parameters.length;i++){
            if(i==parameters.length-1){
                sb.append(parameters[i].getType().getSimpleName()).append(" ").append(parameters[i].getName());
            }else {
                sb.append(parameters[i].getType().getSimpleName()).append(" ").append(parameters[i].getName()).append(", ");
            }
        }
        String methodInfo = method.getReturnType().getSimpleName()+" "+method.getName()+"("+sb.toString()+")";
        //写出第四段
        operatorText(document,methodInfo);
    }

    /**
     * 调用协议
     * @param document
     * @param method
     * @param clz
     */
    public static void writeProtocol(XWPFDocument document,Method method,Class clz){
        // 创建第三个段落
        XWPFParagraph p3 = document.createParagraph();
        //启动
        XWPFRun r3 = p3.createRun();
        // 是否加粗
        r3.setBold(true);
        //设置文本
        r3.setText("4)"+"调用协议");
        // 增加换行
        r3.addCarriageReturn();
        // 设置字体
        r3.setFontFamily("仿宋");
        r3.setFontSize(11);// 字体大小

        String protocol = "该方法通过dubbo协议进行调用";
        //写出第四段
        operatorText(document,protocol);
    }

    /**
     * 引用服务
     * @param document
     * @param method
     * @param clz
     */
    public static void writeInference(XWPFDocument document,Method method,Class clz){
        // 创建第三个段落
        XWPFParagraph p3 = document.createParagraph();
        //启动
        XWPFRun r3 = p3.createRun();
        // 是否加粗
        r3.setBold(true);
        //设置文本
        r3.setText("5)"+"引用服务");
        // 增加换行
        r3.addCarriageReturn();
        // 设置字体
        r3.setFontFamily("仿宋");
        r3.setFontSize(11);// 字体大小


        String reference1 = "在spring Provider配置文件中引入"+clz.getSimpleName();
        String reference2 = "<dubbo:reference id=\""+(new StringBuilder()).append(Character.toLowerCase(clz.getName().charAt(0))).append(clz.getName().substring(1)).toString()+"\" interface=\""+clz.getPackage().getName()+"\"/>";
        //写出第四段
        operatorText(document,reference1);
        operatorText(document,reference2);
    }

    /**
     * 输入参数说明
     * @param document
     * @param method
     * @param clz
     */
    public static void writeInputParam(XWPFDocument document,Method method,Class clz){
        // 创建第三个段落
        XWPFParagraph p3 = document.createParagraph();
        //启动
        XWPFRun r3 = p3.createRun();
        // 是否加粗
        r3.setBold(true);
        //设置文本
        r3.setText("6)"+"输入参数说明");
        // 增加换行
        r3.addCarriageReturn();
        // 设置字体
        r3.setFontFamily("仿宋");
        r3.setFontSize(11);// 字体大小

        //输入参数表格
        operatorTable(document,method,clz);
    }

    /**
     * 输出参数说明
     * @param document
     * @param method
     * @param clz
     */
    public static void writeOutputParam(XWPFDocument document,Method method,Class clz){
        // 创建第三个段落
        XWPFParagraph p3 = document.createParagraph();
        //启动
        XWPFRun r3 = p3.createRun();
        // 是否加粗
        r3.setBold(true);
        //设置文本
        r3.setText("7)"+"输出参数说明");
        // 增加换行
        r3.addCarriageReturn();
        // 设置字体
        r3.setFontFamily("仿宋");
        r3.setFontSize(11);// 字体大小
        //输出参数表格
        int rowSize = 1;
        int colSize = 6;
        XWPFTable table = document.createTable(1,6);
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        //指定表格类型，不可自动缩伸
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(new BigInteger("9000"));
        XWPFTableRow row1 = table.getRow(0);
        setCellType(row1,colSize,true);

        Class<?> returnType = method.getReturnType();
        XWPFTableRow row2 = table.createRow();
        setCellType(row2,colSize,false);
        setOutParamCellFont(row2,colSize,returnType);
    }

    /**
     * 示例代码
     * @param document
     * @param method
     * @param clz
     */
    public static void writeExaCod(XWPFDocument document,Method method,Class clz){
        // 创建第三个段落
        XWPFParagraph p3 = document.createParagraph();
        //启动
        XWPFRun r3 = p3.createRun();
        // 是否加粗
        r3.setBold(true);
        //设置文本
        r3.setText("8)"+"示例代码");
        // 增加换行
        r3.addCarriageReturn();
        // 设置字体
        r3.setFontFamily("仿宋");
        r3.setFontSize(11);// 字体大小
        StringBuilder sb = new StringBuilder();
        sb.append(method.getReturnType().getSimpleName()).append(" ");
        sb.append(Character.toLowerCase(method.getReturnType().getSimpleName().charAt(0))).append(method.getReturnType().getSimpleName().substring(1)).toString();
        sb.append(" ").append("=").append(" ");
        sb.append(clz.getSimpleName()).append(".").append(method.getName()).append("(");
        Parameter[] parameters = method.getParameters();
        for(int i=0;i<parameters.length;i++){
            if(i==parameters.length-1){
                sb.append(parameters[i].getType().getSimpleName()).append(" ").append(parameters[i].getName());
            }else {
                sb.append(parameters[i].getType().getSimpleName()).append(" ").append(parameters[i].getName()).append(", ");
            }
        }
        String exampleCode = sb.append(")").toString();
        //写出第四段
        operatorText(document,exampleCode);
    }

    /**
     * 第四标题段落书写
     * @param document
     * @param content
     */
    public static void operatorText(XWPFDocument document, String content){
        // 创建第四个段落
        XWPFParagraph p4 = document.createParagraph();
        //跑起来
        XWPFRun r4 = p4.createRun();
        //设置内容
        r4.setText(content);
        // 增加换行
        r4.addCarriageReturn();
        // 设置字体
        r4.setFontFamily("Arial");
        r4.setFontSize(11);// 字体大小
    }

    public static void operatorTable(XWPFDocument document,Method method,Class clz){
        int rowSize = 1;
        int colSize = 6;
        XWPFTable table = document.createTable(rowSize,colSize);
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        //指定表格类型，不可自动缩伸
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(new BigInteger("9000"));
        XWPFTableRow row1 = table.getRow(0);
        //设置单元格样式
        setCellType(row1,colSize,true);
        Parameter[] parameters = method.getParameters();
        for (int i=0;i<parameters.length;i++){
            XWPFTableRow row = table.createRow();
            //设置单元格格式
            setCellType(row,rowSize,false);
            //设置单元格字体样式
            setInputParamCellFont(row,colSize,parameters[i]);
        }
    }

    /**
     * 设置输入参数表格中的字体样式
     * @param row
     * @param colSize
     * @param returnType
     */
    private static void setOutParamCellFont(XWPFTableRow row,int colSize,Class returnType){

        for(int i=0;i<colSize;i++){
            //表头数据填充
            String headContent = "";
            switch (i){
                case 0:
                    headContent = new StringBuilder().append(Character.toLowerCase(returnType.getSimpleName().charAt(0))).append(returnType.getSimpleName().substring(1)).toString();
                    break;
                case 1:
                    headContent = "参数名称";
                    break;
                case 2:
                    headContent = returnType.getSimpleName();
                    break;
                case 3:
                    headContent = "";
                    break;
                case 4:
                    headContent = "YES";
                    break;
                case 5:
                    headContent = "";
                    break;
                default:
                    headContent="渣渣辉";
                    break;
            }
            //设置表格中字体
            XWPFRun run = row.getCell(i).addParagraph().createRun();
            run.setFontFamily("Arial");
            run.setText(headContent);
            run.setFontSize(11);
            run.setBold(false);
            run.setColor("000000");
        }
    }



    /**
     * 设置输入参数表格中的字体样式
     * @param row
     * @param colSize
     * @param parameter
     */
    private static void setInputParamCellFont(XWPFTableRow row,int colSize,Parameter parameter){

        for(int i=0;i<colSize;i++){
            //表头数据填充
            String headContent = "";
            switch (i){
                case 0:
                    headContent = parameter.getName();
                    break;
                case 1:
                    headContent = "参数名称";
                    break;
                case 2:
                    headContent = parameter.getType().getSimpleName();
                    break;
                case 3:
                    headContent = "";
                    break;
                case 4:
                    headContent = "YES";
                    break;
                case 5:
                    headContent = "";
                    break;
                default:
                    headContent="渣渣辉";
                    break;
            }
            //设置表格中字体
            XWPFRun run = row.getCell(i).addParagraph().createRun();
            run.setFontFamily("Arial");
            run.setText(headContent);
            run.setFontSize(11);
            run.setBold(false);
            run.setColor("000000");
        }
    }

    /**
     * 设置表格单元格样式
     * @param row
     * @param colSize
     */
    private static void setCellType(XWPFTableRow row,int colSize,boolean isHeader){
        if(isHeader){
            for(int i=0;i<colSize;i++){
                //获取表格样式属性
                CTTcPr ctTcPr = row.getCell(i).getCTTc().addNewTcPr();
                //设置表格宽度
                CTTblWidth ctTblWidth = ctTcPr.addNewTcW();
                ctTblWidth.setType(STTblWidth.DXA);
                ctTblWidth.setW(BigInteger.valueOf(360*5));
                //设置表格内容  位置
                CTVerticalJc ctVerticalJc = ctTcPr.addNewVAlign();
                ctVerticalJc.setVal(STVerticalJc.CENTER);
                //设置表格底色
                CTShd ctShd = ctTcPr.addNewShd();
                ctShd.setColor("auto");
                ctShd.setVal(STShd.CLEAR);
                ctShd.setFill("0066CC");
                //表头数据填充
                String headContent = "";
                switch (i){
                    case 0:
                        headContent = "参数标识";
                        break;
                    case 1:
                        headContent = "参数名称";
                        break;
                    case 2:
                        headContent = "参数类型";
                        break;
                    case 3:
                        headContent = "参数长度";
                        break;
                    case 4:
                        headContent = "是否必须";
                        break;
                    case 5:
                        headContent = "参数描述";
                        break;
                    default:
                        headContent = "渣渣辉";
                        break;
                }
                //设置表格中字体
                XWPFRun run = row.getCell(i).addParagraph().createRun();
                run.setText(headContent);
                run.setFontSize(11);
                run.setBold(true);
                run.setColor("FFFFFF");
            }
        }else{
            for(int i=0;i<colSize;i++){
                CTTcPr ctTcPr = row.getCell(i).getCTTc().addNewTcPr();
                CTTblWidth ctTblWidth = ctTcPr.addNewTcW();
                ctTblWidth.setType(STTblWidth.DXA);
                ctTblWidth.setW(BigInteger.valueOf(360*5));
                CTVerticalJc ctVerticalJc = ctTcPr.addNewVAlign();
                ctVerticalJc.setVal(STVerticalJc.CENTER);
            }
        }
    }

    /**
     * 设置表格行样式
     * @param row
     * @param rowSize
     */
    private static void setRowType(XWPFTableRow row,int rowSize){
        CTTrPr ctTrPr = row.getCtRow().addNewTrPr();
        CTHeight ctHeight = ctTrPr.addNewTrHeight();
        ctHeight.setVal(BigInteger.valueOf(360));
    }
}
