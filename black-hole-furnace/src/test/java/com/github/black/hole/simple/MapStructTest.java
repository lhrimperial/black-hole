package com.github.black.hole.simple;

import com.github.black.hole.simple.mapstruct.mapping.Student;
import com.github.black.hole.simple.mapstruct.mapping.StudentConvert;
import com.github.black.hole.simple.mapstruct.mapping.StudentDTO;
import com.github.black.hole.simple.mapstruct.polymerize.GoodInfo;
import com.github.black.hole.simple.mapstruct.polymerize.GoodInfoConvert;
import com.github.black.hole.simple.mapstruct.polymerize.GoodInfoDTO;
import com.github.black.hole.simple.mapstruct.polymerize.GoodType;
import com.github.black.hole.simple.mapstruct.simple.OrderConvert;
import com.github.black.hole.simple.mapstruct.simple.OrderDO;
import com.github.black.hole.simple.mapstruct.simple.OrderDTO;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
public class MapStructTest {

    @Test
    public void test3() {
        Student student =
                Student.builder()
                        .id(1L)
                        .name("John")
                        .age(18)
                        .admissionTime(LocalDateTime.now())
                        .sex(0)
                        .build();
        StudentConvert studentConvert = Mappers.getMapper(StudentConvert.class);
        StudentDTO studentDTO = studentConvert.from(student);

        System.out.println("student:    {}" + student);
        System.out.println("studentDTO: {}" + studentDTO);
    }

    @Test
    public void test1() {
        GoodInfo goodInfo =
                GoodInfo.builder()
                        .id(1L)
                        .title("Mybatis技术内幕")
                        .price(79.00)
                        .order(100)
                        .typeId(2L)
                        .build();

        GoodType goodType = GoodType.builder().id(2L).name("计算机").show(1).order(3).build();

        GoodInfoConvert convert = Mappers.getMapper(GoodInfoConvert.class);
        GoodInfoDTO goodInfoDTO = convert.from(goodInfo, goodType);
        System.out.println("goodInfo:    " + goodInfo);
        System.out.println("goodType:    " + goodType);
        System.out.println("goodInfoDTO: " + goodInfoDTO);
    }

    @Test
    public void test() {
        OrderDO order =
                OrderDO.builder()
                        .id(123L)
                        .buyerPhone("13707318123")
                        .buyerAddress("中电软件园")
                        .amount(10000L)
                        .payStatus(1)
                        .createTime(LocalDateTime.now())
                        .build();

        OrderConvert orderConvert = Mappers.getMapper(OrderConvert.class);
        OrderDTO orderDTO = orderConvert.from(order);

        System.out.println("order:    " + order);
        System.out.println("orderDTO: " + orderDTO);
    }
}
