package com.gmy.blog.work;

import cn.hutool.core.util.StrUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gmydl
 * @title: OCR
 * @projectName blog-api
 * @description: TODO
 * @date 2022/7/30 13:49
 */
public class OCR {

    public static List<OcrSingleTextBO> singleTextList = new ArrayList<>(16);

    static {
        singleTextList.add(new OcrSingleTextBO("聘用单位", new BigDecimal("0.999"), new Points(375, 632)));
        singleTextList.add(new OcrSingleTextBO("身份证号", new BigDecimal("0.999"), new Points(340, 627)));
        singleTextList.add(new OcrSingleTextBO("焊工资格证书编号", new BigDecimal("0.999"), new Points(409, 606)));
        singleTextList.add(new OcrSingleTextBO("焊工资格证有效期", new BigDecimal("0.999"), new Points(446, 603)));
        singleTextList.add(new OcrSingleTextBO("210", new BigDecimal("0.999"), new Points(501, 553)));
        singleTextList.add(new OcrSingleTextBO("质保专用", new BigDecimal("0.999"), new Points(562, 555)));
        singleTextList.add(new OcrSingleTextBO("朱文星", new BigDecimal("0.999"), new Points(305, 501)));
        singleTextList.add(new OcrSingleTextBO("HDWM-17", new BigDecimal("0.999"), new Points(410, 481)));
        singleTextList.add(new OcrSingleTextBO("2022-2-2", new BigDecimal("0.999"), new Points(447, 478)));
        singleTextList.add(new OcrSingleTextBO("320052200002052017", new BigDecimal("0.999"), new Points(340, 462)));
        singleTextList.add(new OcrSingleTextBO("某某某机械有限公司", new BigDecimal("0.999"), new Points(376, 445)));

    }

    @Test
    public void dataHandler(){
        List<OcrSingleTextBO> singleTextList = new ArrayList<>();
        singleTextList.add(new OcrSingleTextBO("聘用单位", new BigDecimal("0.999"), new Points(375, 632)));
        singleTextList.add(new OcrSingleTextBO("身份证号", new BigDecimal("0.999"), new Points(340, 627)));
        singleTextList.add(new OcrSingleTextBO("焊工资格证书编号", new BigDecimal("0.999"), new Points(409, 606)));
        singleTextList.add(new OcrSingleTextBO("焊工资格证有效期", new BigDecimal("0.999"), new Points(446, 603)));
        singleTextList.add(new OcrSingleTextBO("210", new BigDecimal("0.999"), new Points(501, 553)));
        singleTextList.add(new OcrSingleTextBO("质保专用", new BigDecimal("0.999"), new Points(562, 555)));
        singleTextList.add(new OcrSingleTextBO("朱文星", new BigDecimal("0.999"), new Points(305, 501)));
        singleTextList.add(new OcrSingleTextBO("HDWM-17", new BigDecimal("0.999"), new Points(410, 481)));
        singleTextList.add(new OcrSingleTextBO("2022-2-2", new BigDecimal("0.999"), new Points(447, 478)));
        singleTextList.add(new OcrSingleTextBO("320052200002052017", new BigDecimal("0.999"), new Points(340, 462)));
        singleTextList.add(new OcrSingleTextBO("某某某机械有限公司", new BigDecimal("0.999"), new Points(376, 445)));


        //  X 轴相同 或者 绝对值有差值，说明对应的字段
        // 思路：
        // 一个list中，
        ArrayList<OcrSingleTextBO> dataHandlerList = new ArrayList<>(singleTextList);
        HashMap<String, String> result = new HashMap<>();


        dataHandlerList.forEach( item ->{
            // 坐标
            Points points = item.getPoints();
            //
            Integer valueX = points.getX();

            // 要删除的元素索引
            int deleteDataIndex = 0;
            boolean isFind = false;
            for (int i = 0; i < singleTextList.size(); i++) {
                OcrSingleTextBO it = singleTextList.get(i);
                if (it.getPoints().getX().equals(valueX) || Math.abs(valueX - it.getPoints().getX()) <= 5) {
                    if (!Objects.equals(it.getText(), item.getText())){
                        String existence = result.get(it.getText());
                        if (StringUtil.isBlank(existence)){
                            result.put(item.getText(), it.getText());
                        }
                        deleteDataIndex = i;
                        isFind = true;
                    }
                }
            }
            if (isFind) {
                singleTextList.remove(deleteDataIndex);
            }

        });
        System.out.println(result);

        result.forEach( (k, info) ->{
            String k1 = "身份证号";
            String k2 = "证号";
            String k3 = "证件编号";
            if (k.contains("身份证") || k.contains(k2) || k.contains(k3)){
                //
                if (k.length() == 18) {
                    // 保存数据
                }

            } else if (k.contains("姓名")){

            }

        });

    }

    @Test
    public void iterator(){

        ArrayList<OcrSingleTextBO> tempText = new ArrayList<>(OCR.singleTextList);
        HashMap<String, String> resultInfo = new HashMap<>(16);
        tempText.forEach(item ->{
            // X的坐标
            Integer valueX = item.getPoints().getX();

            // 遍历文本
            Iterator<OcrSingleTextBO> iterator = OCR.singleTextList.iterator();
            while (iterator.hasNext()) {
                OcrSingleTextBO it = iterator.next();
                if (it.getPoints().getX().equals(valueX) || Math.abs(valueX - it.getPoints().getX()) <= 5) {
                    if (!Objects.equals(it.getText(), item.getText())) {
                        resultInfo.put(item.getText(), it.getText());
                        // 移除元素，
                        iterator.remove();
                    }
                }
            }
        });

        System.out.println(resultInfo);


    }

    public static void main(String[] args) {

        String  test2 = "焊工资格证书编号：HDWM001-17专用焊工项目考试工艺课定编号：PQR2017020";
        String  data2 = "证书编号";
        boolean b = Pattern.compile(data2).matcher(test2).find();
        System.out.println(b);
        int index = test2.indexOf(data2);
        String substring1 = test2.substring(index, index + data2.length());
        System.out.println(substring1);
        System.out.println(test2.contains("："));


        final String CN_COLON = "：";
        String data = "2020年01月18日撒打算大阿斯顿";
        int indexOf = data.indexOf(CN_COLON);
        System.out.println(data.substring(0, 11));
        String name = "关梦洋（刚银行：";
        int indexOf1 = name.indexOf(CN_COLON);
        System.out.println(indexOf1);

        String name2 = "关梦洋性别：男：";
        String dataInfo = name.substring(0, name2.indexOf("性别"));
        System.out.println(dataInfo);

        String idNumber = "33042119880812381x";
        boolean isId = isIDNumber(idNumber);
        System.out.println(isId);


    }


    public static boolean isIDNumber(String IDNumber) {
        if (StringUtil.isBlank(IDNumber)){
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    // 前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    // 这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].equalsIgnoreCase(String.valueOf(idCardLast))) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }



    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }


}



@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("OCR识别图片单个文本结果")
@ToString
class OcrSingleTextBO {

    @ApiModelProperty("文本内容")
    private String text;

    @ApiModelProperty("识别得分")
    private BigDecimal score;

    @ApiModelProperty("文本中心坐标")
    private Points points;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("OCR文本中心坐标")
class Points {

    @ApiModelProperty("X轴坐标")
    private Integer x;

    @ApiModelProperty("Y轴坐标")
    private Integer y;

}