package kr.co.wikibook.gallery.cart.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartPostReq {
  private int memberId;
  // 원래 setter 빼고 세션에 있는 사용자의 pk값을 가져와야함
  private int itemId;
}
