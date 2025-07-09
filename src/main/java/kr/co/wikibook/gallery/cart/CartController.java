package kr.co.wikibook.gallery.cart;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.wikibook.gallery.account.etc.AccountConstants;
import kr.co.wikibook.gallery.cart.model.CartDeleteReq;
import kr.co.wikibook.gallery.cart.model.CartGetRes;
import kr.co.wikibook.gallery.cart.model.CartPostReq;
import kr.co.wikibook.gallery.common.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")

public class CartController {
  private final CartService cartService;

  @PostMapping
  public ResponseEntity<?> addCart(HttpServletRequest httpreq, @RequestBody CartPostReq req) {
    log.info("req:{}", req);
    int logginedMemberId = (int)HttpUtils.getSessionValue(httpreq, AccountConstants.MEMBER_ID_NAME);
    req.setMemberId(logginedMemberId);
    int result = cartService.save(req);
    return ResponseEntity.ok(result);
  }

  @GetMapping
  public ResponseEntity<?> getCart(HttpServletRequest httpreq) {
    int logginedMemberId = (int)HttpUtils.getSessionValue(httpreq, AccountConstants.MEMBER_ID_NAME);
    List<CartGetRes> result = cartService.findAll(logginedMemberId);
    return ResponseEntity.ok(result);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteCart(HttpServletRequest httpreq, @RequestParam(name = "id") int req) {
    log.info("req:{}", req);
    int logginedMemberId = (int)HttpUtils.getSessionValue(httpreq, AccountConstants.MEMBER_ID_NAME);
    CartDeleteReq cartDeleteReq = new CartDeleteReq(logginedMemberId, req);
    int result = cartService.remove(cartDeleteReq);
    return ResponseEntity.ok(result);
  }
}
