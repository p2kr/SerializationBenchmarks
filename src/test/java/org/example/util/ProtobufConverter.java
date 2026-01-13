package org.example.util;

import com.google.protobuf.ByteString;
import org.example.DeepNestedPojo;
import org.example.LargePojo;
import org.example.NestedPojo;
import org.example.proto.DeepNestedProto;
import org.example.proto.LargePojoProto;
import org.example.proto.NestedProto;

import java.util.List;

public class ProtobufConverter {

    public static LargePojoProto.LargePojoList convertListToProto(List<LargePojo> pojos) {
        LargePojoProto.LargePojoList.Builder listBuilder = LargePojoProto.LargePojoList.newBuilder();
        for (LargePojo pojo : pojos) {
            listBuilder.addItems(convertToProto(pojo));
        }
        return listBuilder.build();
    }

    public static LargePojoProto.LargePojo convertToProto(LargePojo pojo) {
        LargePojoProto.LargePojo.Builder builder = LargePojoProto.LargePojo.newBuilder();

        // Convert nested objects
        if (pojo.getRef1() != null) builder.setRef1(convertNestedToProto(pojo.getRef1()));
        if (pojo.getRef2() != null) builder.setRef2(convertNestedToProto(pojo.getRef2()));
        if (pojo.getRef3() != null) builder.setRef3(convertNestedToProto(pojo.getRef3()));
        if (pojo.getRef4() != null) builder.setRef4(convertNestedToProto(pojo.getRef4()));
        if (pojo.getRef5() != null) builder.setRef5(convertNestedToProto(pojo.getRef5()));
        if (pojo.getRef6() != null) builder.setRef6(convertNestedToProto(pojo.getRef6()));
        if (pojo.getRef7() != null) builder.setRef7(convertNestedToProto(pojo.getRef7()));
        if (pojo.getRef8() != null) builder.setRef8(convertNestedToProto(pojo.getRef8()));
        if (pojo.getRef9() != null) builder.setRef9(convertNestedToProto(pojo.getRef9()));
        if (pojo.getRef10() != null) builder.setRef10(convertNestedToProto(pojo.getRef10()));
        if (pojo.getRef11() != null) builder.setRef11(convertNestedToProto(pojo.getRef11()));
        if (pojo.getRef12() != null) builder.setRef12(convertNestedToProto(pojo.getRef12()));
        if (pojo.getRef13() != null) builder.setRef13(convertNestedToProto(pojo.getRef13()));
        if (pojo.getRef14() != null) builder.setRef14(convertNestedToProto(pojo.getRef14()));
        if (pojo.getRef15() != null) builder.setRef15(convertNestedToProto(pojo.getRef15()));
        if (pojo.getRef16() != null) builder.setRef16(convertNestedToProto(pojo.getRef16()));
        if (pojo.getRef17() != null) builder.setRef17(convertNestedToProto(pojo.getRef17()));
        if (pojo.getRef18() != null) builder.setRef18(convertNestedToProto(pojo.getRef18()));
        if (pojo.getRef19() != null) builder.setRef19(convertNestedToProto(pojo.getRef19()));
        if (pojo.getRef20() != null) builder.setRef20(convertNestedToProto(pojo.getRef20()));

        // Primitive fields (proto3 automatically handles defaults)
        builder.setField21(pojo.getField21());
        builder.setField22(pojo.getField22());
        builder.setField23(pojo.getField23());
        builder.setField24(pojo.getField24());
        builder.setField25(pojo.getField25());
        builder.setField26(pojo.getField26());
        builder.setField27(pojo.getField27());
        builder.setField28(pojo.getField28());
        builder.setField29(pojo.getField29());
        builder.setField30(pojo.getField30());

        builder.setField31(pojo.getField31());
        builder.setField32(pojo.getField32());
        builder.setField33(pojo.getField33());
        builder.setField34(pojo.getField34());
        builder.setField35(pojo.getField35());
        builder.setField36(pojo.getField36());
        builder.setField37(pojo.getField37());
        builder.setField38(pojo.getField38());
        builder.setField39(pojo.getField39());
        builder.setField40(pojo.getField40());

        builder.setField41(pojo.getField41());
        builder.setField42(pojo.getField42());
        builder.setField43(pojo.getField43());
        builder.setField44(pojo.getField44());
        builder.setField45(pojo.getField45());
        builder.setField46(pojo.getField46());
        builder.setField47(pojo.getField47());
        builder.setField48(pojo.getField48());
        builder.setField49(pojo.getField49());
        builder.setField50(pojo.getField50());

        builder.setField51(pojo.isField51());
        builder.setField52(pojo.isField52());
        builder.setField53(pojo.isField53());
        builder.setField54(pojo.isField54());
        builder.setField55(pojo.isField55());
        builder.setField56(pojo.isField56());
        builder.setField57(pojo.isField57());
        builder.setField58(pojo.isField58());
        builder.setField59(pojo.isField59());
        builder.setField60(pojo.isField60());

        builder.setField61(pojo.getField61());
        builder.setField62(pojo.getField62());
        builder.setField63(pojo.getField63());
        builder.setField64(pojo.getField64());
        builder.setField65(pojo.getField65());
        builder.setField66(pojo.getField66());
        builder.setField67(pojo.getField67());
        builder.setField68(pojo.getField68());
        builder.setField69(pojo.getField69());
        builder.setField70(pojo.getField70());

        builder.setField71(pojo.getField71());
        builder.setField72(pojo.getField72());
        builder.setField73(pojo.getField73());
        builder.setField74(pojo.getField74());
        builder.setField75(pojo.getField75());
        builder.setField76(pojo.getField76());
        builder.setField77(pojo.getField77());
        builder.setField78(pojo.getField78());
        builder.setField79(pojo.getField79());
        builder.setField80(pojo.getField80());

        builder.setField81(pojo.getField81());
        builder.setField82(pojo.getField82());
        builder.setField83(pojo.getField83());
        builder.setField84(pojo.getField84());
        builder.setField85(pojo.getField85());
        builder.setField86(pojo.getField86());
        builder.setField87(pojo.getField87());
        builder.setField88(pojo.getField88());
        builder.setField89(pojo.getField89());
        builder.setField90(pojo.getField90());

        builder.setField91(pojo.getField91());
        builder.setField92(pojo.getField92());
        builder.setField93(pojo.getField93());
        builder.setField94(pojo.getField94());
        builder.setField95(pojo.getField95());
        builder.setField96(pojo.getField96());
        builder.setField97(pojo.getField97());
        builder.setField98(pojo.getField98());
        builder.setField99(pojo.getField99());
        builder.setField100(pojo.getField100());

        // Boxed types
        if (pojo.getField101() != null) builder.setField101(pojo.getField101());
        if (pojo.getField102() != null) builder.setField102(pojo.getField102());
        if (pojo.getField103() != null) builder.setField103(pojo.getField103());
        if (pojo.getField104() != null) builder.setField104(pojo.getField104());
        if (pojo.getField105() != null) builder.setField105(pojo.getField105());
        if (pojo.getField106() != null) builder.setField106(pojo.getField106());
        if (pojo.getField107() != null) builder.setField107(pojo.getField107());
        if (pojo.getField108() != null) builder.setField108(pojo.getField108());
        if (pojo.getField109() != null) builder.setField109(pojo.getField109());
        if (pojo.getField110() != null) builder.setField110(pojo.getField110());
        if (pojo.getField111() != null) builder.setField111(pojo.getField111());
        if (pojo.getField112() != null) builder.setField112(pojo.getField112());

        // Collections
        if (pojo.getField113() != null) builder.addAllField113(pojo.getField113());
        if (pojo.getField114() != null) builder.addAllField114(pojo.getField114());
        if (pojo.getField115() != null) builder.putAllField115(pojo.getField115());

        // String fields
        if (pojo.getField116() != null) builder.setField116(pojo.getField116());
        if (pojo.getField117() != null) builder.setField117(pojo.getField117());
        if (pojo.getField118() != null) builder.setField118(pojo.getField118());
        if (pojo.getField119() != null) builder.setField119(pojo.getField119());
        if (pojo.getField120() != null) builder.setField120(pojo.getField120());
        if (pojo.getField121() != null) builder.setField121(pojo.getField121());
        if (pojo.getField122() != null) builder.setField122(pojo.getField122());
        if (pojo.getField123() != null) builder.setField123(pojo.getField123());
        if (pojo.getField124() != null) builder.setField124(pojo.getField124());
        if (pojo.getField125() != null) builder.setField125(pojo.getField125());
        if (pojo.getField126() != null) builder.setField126(pojo.getField126());
        if (pojo.getField127() != null) builder.setField127(pojo.getField127());
        if (pojo.getField128() != null) builder.setField128(pojo.getField128());
        if (pojo.getField129() != null) builder.setField129(pojo.getField129());
        if (pojo.getField130() != null) builder.setField130(pojo.getField130());
        if (pojo.getField131() != null) builder.setField131(pojo.getField131());
        if (pojo.getField132() != null) builder.setField132(pojo.getField132());
        if (pojo.getField133() != null) builder.setField133(pojo.getField133());
        if (pojo.getField134() != null) builder.setField134(pojo.getField134());
        if (pojo.getField135() != null) builder.setField135(pojo.getField135());
        if (pojo.getField136() != null) builder.setField136(pojo.getField136());
        if (pojo.getField137() != null) builder.setField137(pojo.getField137());
        if (pojo.getField138() != null) builder.setField138(pojo.getField138());
        if (pojo.getField139() != null) builder.setField139(pojo.getField139());
        if (pojo.getField140() != null) builder.setField140(pojo.getField140());
        if (pojo.getField141() != null) builder.setField141(pojo.getField141());
        if (pojo.getField142() != null) builder.setField142(pojo.getField142());
        if (pojo.getField143() != null) builder.setField143(pojo.getField143());
        if (pojo.getField144() != null) builder.setField144(pojo.getField144());
        if (pojo.getField145() != null) builder.setField145(pojo.getField145());
        if (pojo.getField146() != null) builder.setField146(pojo.getField146());
        if (pojo.getField147() != null) builder.setField147(pojo.getField147());
        if (pojo.getField148() != null) builder.setField148(pojo.getField148());
        if (pojo.getField149() != null) builder.setField149(pojo.getField149());
        if (pojo.getField150() != null) builder.setField150(pojo.getField150());
        if (pojo.getField151() != null) builder.setField151(pojo.getField151());
        if (pojo.getField152() != null) builder.setField152(pojo.getField152());
        if (pojo.getField153() != null) builder.setField153(pojo.getField153());
        if (pojo.getField154() != null) builder.setField154(pojo.getField154());
        if (pojo.getField155() != null) builder.setField155(pojo.getField155());
        if (pojo.getField156() != null) builder.setField156(pojo.getField156());
        if (pojo.getField157() != null) builder.setField157(pojo.getField157());
        if (pojo.getField158() != null) builder.setField158(pojo.getField158());
        if (pojo.getField159() != null) builder.setField159(pojo.getField159());
        if (pojo.getField160() != null) builder.setField160(pojo.getField160());
        if (pojo.getField161() != null) builder.setField161(pojo.getField161());
        if (pojo.getField162() != null) builder.setField162(pojo.getField162());
        if (pojo.getField163() != null) builder.setField163(pojo.getField163());
        if (pojo.getField164() != null) builder.setField164(pojo.getField164());
        if (pojo.getField165() != null) builder.setField165(pojo.getField165());
        if (pojo.getField166() != null) builder.setField166(pojo.getField166());
        if (pojo.getField167() != null) builder.setField167(pojo.getField167());
        if (pojo.getField168() != null) builder.setField168(pojo.getField168());
        if (pojo.getField169() != null) builder.setField169(pojo.getField169());
        if (pojo.getField170() != null) builder.setField170(pojo.getField170());
        if (pojo.getField171() != null) builder.setField171(pojo.getField171());
        if (pojo.getField172() != null) builder.setField172(pojo.getField172());
        if (pojo.getField173() != null) builder.setField173(pojo.getField173());
        if (pojo.getField174() != null) builder.setField174(pojo.getField174());
        if (pojo.getField175() != null) builder.setField175(pojo.getField175());
        if (pojo.getField176() != null) builder.setField176(pojo.getField176());
        if (pojo.getField177() != null) builder.setField177(pojo.getField177());
        if (pojo.getField178() != null) builder.setField178(pojo.getField178());
        if (pojo.getField179() != null) builder.setField179(pojo.getField179());
        if (pojo.getField180() != null) builder.setField180(pojo.getField180());
        if (pojo.getField181() != null) builder.setField181(pojo.getField181());
        if (pojo.getField182() != null) builder.setField182(pojo.getField182());
        if (pojo.getField183() != null) builder.setField183(pojo.getField183());
        if (pojo.getField184() != null) builder.setField184(pojo.getField184());
        if (pojo.getField185() != null) builder.setField185(pojo.getField185());
        if (pojo.getField186() != null) builder.setField186(pojo.getField186());
        if (pojo.getField187() != null) builder.setField187(pojo.getField187());
        if (pojo.getField188() != null) builder.setField188(pojo.getField188());
        if (pojo.getField189() != null) builder.setField189(pojo.getField189());
        if (pojo.getField190() != null) builder.setField190(pojo.getField190());
        if (pojo.getField191() != null) builder.setField191(pojo.getField191());
        if (pojo.getField192() != null) builder.setField192(pojo.getField192());
        if (pojo.getField193() != null) builder.setField193(pojo.getField193());
        if (pojo.getField194() != null) builder.setField194(pojo.getField194());
        if (pojo.getField195() != null) builder.setField195(pojo.getField195());
        if (pojo.getField196() != null) builder.setField196(pojo.getField196());
        if (pojo.getField197() != null) builder.setField197(pojo.getField197());
        if (pojo.getField198() != null) builder.setField198(pojo.getField198());
        if (pojo.getField199() != null) builder.setField199(pojo.getField199());
        if (pojo.getField200() != null) builder.setField200(pojo.getField200());

        return builder.build();
    }

    private static NestedProto.Nested convertNestedToProto(NestedPojo nested) {
        NestedProto.Nested.Builder builder = NestedProto.Nested.newBuilder();

        if (nested.getField1() != null) builder.setField1(nested.getField1());
        if (nested.getField2() != null) builder.setField2(nested.getField2());
        if (nested.getField3() != null) builder.setField3(nested.getField3());
        if (nested.getField4() != null) builder.setField4(nested.getField4());
        if (nested.getField5() != null) builder.setField5(nested.getField5());
        if (nested.getLongField1() != null) builder.setLongField1(nested.getLongField1());
        if (nested.getIntField1() != null) builder.setIntField1(nested.getIntField1());
        if (nested.getDoubleField1() != null) builder.setDoubleField1(nested.getDoubleField1());
        if (nested.getDeepNested() != null) builder.setDeepNested(convertDeepNestedToProto(nested.getDeepNested()));

        return builder.build();
    }

    private static DeepNestedProto.DeepNested convertDeepNestedToProto(DeepNestedPojo deep) {
        DeepNestedProto.DeepNested.Builder builder = DeepNestedProto.DeepNested.newBuilder();

        if (deep.getData() != null) builder.setData(deep.getData());
        if (deep.getBlob() != null) builder.setBlob(ByteString.copyFrom(deep.getBlob()));

        return builder.build();
    }
}
