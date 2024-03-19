package abouttest;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

/*
题目
1、有一家超市，出售苹果和草莓。其中苹果 8 元/斤，草莓 13 元/斤。
现在顾客 A 在超市购买了若干斤苹果和草莓，需要计算一共多少钱？
请编写函数，对于 A 购买的水果斤数 (水果斤数为大于等于 0 的整数)，计算并返回所购买商品的总价。

2、超市增加了一种水果芒果，其定价为 20 元/斤。
现在顾客 B 在超市购买了若干斤苹果、 草莓和芒果，需计算一共需要多少钱？
请编写函数，对于 B 购买的水果斤数 (水果斤数为大于等于 0 的整数)，计算并返回所购买商品的总价。

3、超市做促销活动，草莓限时打 8 折。
现在顾客 C 在超市购买了若干斤苹果、 草莓和芒果，需计算一共需要多少钱？
请编写函数，对于 C 购买的水果斤数 (水果斤数为大于等于 0 的整数)，计算并返回所购买商品的总价。

4、促销活动效果明显，超市继续加大促销力度，购物满 100 减 10 块。
现在顾客 D 在超市购买了若干斤苹果、 草莓和芒果，需计算一共需要多少钱？
请编写函数，对于 D 购买的水果斤数 (水果斤数为大于等于 0 的整数)，计算并返回所购买商品的总价。

要求
使用 Java 程序编写，IDE 不限，
请使用面向对象的思路进行程序编写。
需在程序中，验证函数计算结果的正确性。

提示
顾客购买的水果斤数，可自行确定。无论数值为多少，均需验证程序计算结果的正确性。
可以编写多个函数分别实现，也可以只编写一个函数实现，方式不限。
面试时，请准备电脑及 IDE 环境进行现场演示。
若能将代码提交到 github、gitee、gitlab 等代码托管仓库，提供代码仓库地址更佳。
 */
public class BuyFruits {



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String fruits = sc.nextLine();//获取第一行，依次输入苹果，草莓，芒果的购买斤数。输入示例1 1 0（空格分隔）,表示苹果1斤，草莓1斤，芒果0斤
        String sbDiscount = sc.nextLine();//获取第二行，依次获取是否打折，打折力度。输入示例0表示不打折，1 80表示打折且打8折
        String promotion = sc.nextLine();//获取第三行，依次获取是否有满减活动，满多少，满一次减多少，输入示例0表示不打折，1 100 10表示有满减且满100减10


        System.out.println("客户购买的商品总价为：" + getTotalPrice(fruits,sbDiscount,promotion) + "元");
    }

    private static double getTotalPrice(String fruits, String sbDiscount, String promotion) {
        /*
    定义客户购买的水果种类及单价说明，水果apple（8元/斤），strawberry（13元/斤），mango（20元/斤）
    购买的水果斤数为大于等于0的整数
    strawberry可能有限时打8折，discountFlag = true 表示打折
    可能有满减活动 promotionFlag = true 表示打折
     */
        int apple;//8元/斤
        int strawberry;//13元/斤
        int mango;//20元/斤
        boolean discountFlag;//默认不打折，由柜员结账输入是否有打折，0表示不打折，1表示打折
        long discount = 0;//打折力度，匹配传两位数字表示打折，如传80表示打八折，75表示打75折
        boolean promotionFlag;//默认无满减活动，由柜员结账输入是否有满减活动，0表示无满减活动，1表示有满减活动
        long promotionAmount = 0;//表示满减活动，要满多少金额才能减，单位元
        long subAmount = 0;//表示满足满减活动需要扣除的金额，满足几次就*几次

        Logger logger =  Logger.getLogger(BuyFruits.class.getName());

        double finalPrice = 0l;
        long tempPrice = 0l;
        long subCount = 0;//满减的次数
        discountFlag = false;
        promotionFlag = false;
        try {
            int[] fruitAar = Arrays.stream(fruits.split(" ")).mapToInt(Integer :: parseInt).toArray();
            int[] sbDiscountAar = Arrays.stream(sbDiscount.split(" ")).mapToInt(Integer :: parseInt).toArray();
            int[] promotionAar = Arrays.stream(promotion.split(" ")).mapToInt(Integer :: parseInt).toArray();
            System.out.println(Arrays.toString(fruitAar));
            System.out.println(Arrays.toString(sbDiscountAar));
            System.out.println(Arrays.toString(promotionAar));

            apple = fruitAar[0];
            strawberry = fruitAar[1];
            mango = fruitAar[2];
            if (sbDiscountAar[0] == 1 && sbDiscountAar[1] >0 ) {
                discountFlag = true;
                discount = sbDiscountAar[1];
            }
            if (promotionAar[0] == 1 && promotionAar[1] >0 && promotionAar[2] > 0) {
                promotionFlag = true;
                promotionAmount = promotionAar[1]*100;
                subAmount = promotionAar[2]*100;
            }

            //金额计算时统一乘100处理，返回结果时再除去100
            if (!discountFlag && !promotionFlag) {
                finalPrice = (800 * apple) + (1300 * strawberry) + (2000 * mango);
            } else if (!discountFlag && promotionFlag) {
                tempPrice = (800 * apple) + (1300 * strawberry) + (2000 * mango);
                subCount = tempPrice/promotionAmount;
                finalPrice = tempPrice - (subAmount * subCount);
            } else if (discountFlag && !promotionFlag) {
                finalPrice = (800 * apple) + (13 * strawberry * discount) + (2000 * mango);
            } else if (discountFlag && promotionFlag) {
                tempPrice = (800 * apple) + (13 * strawberry * discount) + (2000 * mango);
                subCount = tempPrice/promotionAmount;
                finalPrice = tempPrice - (subAmount * subCount);
            }
            finalPrice = finalPrice / 100;

        } catch (Exception e) {
            logger.info("输入数据有误，请检查输入数据是否均为大于等于0的整数");
            return 0l;
        }

        return finalPrice;
    }


}
