package Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class test {
    public static void main(String args[])  {
       // int arith_number;
       // Scanner in=new Scanner(System.in);
        //arith_number=in.nextInt();
    creatArith(3);


    }
    public static  int getNumber(){
        int number=0;
        Random num=new Random();
        number=num.nextInt(101);
        return number;
    }
    public static char getOperator(){
        char operator=0;
        int i=0;
        Random num=new Random();
        i=num.nextInt(2);
        switch (i){
            case 0:
                operator='+';
                break;
            case 1:
                operator='-';
                break;
        }
        return operator;
    }

    public static void creatArith(int arith_number) {

        ArrayList<String> arrayList =null;
        if (arith_number > 0) {
            arrayList = new ArrayList<String>();//用于存放产生的式子
            for (int j = 0; j < arith_number; ) {//一共要产生arith_number个式子
                boolean flag = false;
                int num_number = 0;
                Random n = new Random();
                num_number = n.nextInt(2) + 2;//需要产生2-3个数字
                String arith = "";//用来暂时存储产生的每一个式子

                    for (int i = 0; i < num_number; i++) {//每进行循环出来产生一个随机式子
                        int num = getNumber();
                        String operator = String.valueOf(getOperator());
                        arith += num + operator;
                    }
                String[]temp=new String[1];
                temp[0]=arith.substring(0,arith.length()-1);
                ArrayList<String> temp_result=new ArrayList<String>();
                temp_result=getResult(temp);
                int temp_int=0;
                temp_int=Integer.parseInt(temp_result.get(0));
                if(temp_int<=100&&temp_int>=0){
                    flag=true;
                    j++;
                }

               if(flag) {//如果式子合格，则存入arrayList中
                   arith = arith.substring(0, arith.length() - 1);
                   arrayList.add(arith);
               }
            }//j循环结束，将产生的所有式子们存入arrayList中
        }
        //将arrayList写入文件Exercise.txt;

        try{
            creatFile_1(arrayList);
        }catch (IOException e) {
            e.printStackTrace();
        }
        //以下将arrayList转换成arr数组
        int size=arrayList.size();
        String[] arr=arrayList.toArray(new String[size]);
       ArrayList<String> Result_List=new ArrayList<String>();//用于接收getResult返回的结果集合
      Result_List=getResult(arr);

      try{
          creatFile_2(arrayList,Result_List);
      }catch (IOException e){
          e.printStackTrace();
      }

    }
    public static void creatFile_1(ArrayList<String> arrayList) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("Exercise.txt"));
        for (String s : arrayList) {
            try {
                bw.write(s + "=");
            } catch (IOException e) {
                e.printStackTrace();
            }
            bw.newLine();
            bw.flush();
        }
    }
    public static void creatFile_2(ArrayList<String> arrayList,ArrayList<String> Result_list) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("Answers.txt"));
        for (int i=0;i<arrayList.size();i++) {
            try {
                bw.write(arrayList.get(i) + "="+Result_list.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            bw.newLine();
            bw.flush();
        }
    }
    public  static ArrayList<String> getResult(String[] arr){//计算arr中所有式子的结果
        ArrayList<String> Resultlist=new ArrayList<String>();//储存返回的结果
        for(int i=0;i<arr.length;i++) {//对arr中的每一个算式都要进行数字的拆分
            char[] arr_str = arr[i].toCharArray();
            String[] arr_number = arr[i].split("[+]|[-]");//从一个算式中拆分数字，放入一个字符串数组

            ArrayList<Character> arr_operator_list = new ArrayList<Character>();//       拆分符号
            for (int j = 0; j < arr_str.length; j++) {
                if (arr_str[j] == '+' || arr_str[j] == '-') {
                    arr_operator_list.add(arr_str[j]);
                }
            }
            //截止到现在已经将第i个算式中的num和operator存放好
            int arr_i_result= CaculateResult(arr_number, arr_operator_list);
            Resultlist.add(String.valueOf(arr_i_result));

        }
        return Resultlist;
    }
    public static int CaculateResult(String[] arr_number,ArrayList<Character> arr_operater_List){//返回一个式子的值
        BigDecimal[] num=new BigDecimal[arr_number.length];
        for(int i=0;i<num.length;i++){
            num[i]=new BigDecimal(arr_number[i]);
        }//将装有数字的数组转换成大数
        BigDecimal result=num[0];
        for(int j=0;j<arr_operater_List.size();j++){

            if(arr_operater_List.get(j)=='+'){
                result=result.add(num[j+1]);
            }else if (arr_operater_List.get(j)=='-'){
                result=result.subtract(num[j+1]);
            }
        }
        int result_int=result.intValue();
        return result_int;

    }
}

