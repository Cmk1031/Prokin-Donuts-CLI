package dto.warehouse;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WarehouseDTO {
    /** 창고 아이디 */
    private int warehouseId;
    /** 주소 */
    private String address;
    /** 창고이름 */
    private String warehouseName;
    /** 생성일 */
    private Date createDate;
    /** 수용한도 */
    private int capacityLimit;
    /** 회원번호 */
    private int memberNo;
}
