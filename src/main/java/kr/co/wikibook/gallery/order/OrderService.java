package kr.co.wikibook.gallery.order;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.wikibook.gallery.cart.CartMapper;
import kr.co.wikibook.gallery.item.ItemMapper;
import kr.co.wikibook.gallery.item.ItemService;
import kr.co.wikibook.gallery.item.model.ItemGetRes;
import kr.co.wikibook.gallery.order.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderMapper orderMapper;
  private final ItemMapper itemMapper;
  private final OrderItemMapper orderItemMapper;
  private final CartMapper cartMapper;

  @Transactional
  public int saveOrder(OrderPostReq req, int memberId) {
    // 상품정보 db로부터가져온다
    List<ItemGetRes> data = itemMapper.findAllByIdIn(req.getItemIds());
    log.info("data:{}", data);
    long sum = 0;
    for (ItemGetRes item : data) {
      sum += item.getPrice() - (item.getPrice() * item.getDiscountPer()) / 100;
    }
    log.info("sum:{}", sum);
      OrderPostDto orderPostDto = new OrderPostDto();
      orderPostDto.setMemberId(memberId);
      orderPostDto.setName(req.getName());
      orderPostDto.setAddress(req.getAddress());
      orderPostDto.setPayment(req.getPayment());
      orderPostDto.setCardNumber(req.getCardNumber());
      orderPostDto.setAmount(sum);
    log.info("before orderPostDto:{}", orderPostDto);
//    OrderPostDto dto = new OrderPostDto(memberId, req.getName(), req.getAddress(), req.getPayment(), req.getCardNumber(), sum);
    orderMapper.save(orderPostDto);
    log.info("after orderPostDto:{}", orderPostDto);

    OrderItemPostDto orderItemPostDto = new OrderItemPostDto(orderPostDto.getOrderId(), req.getItemIds());
    orderItemMapper.save(orderItemPostDto);

    cartMapper.deleteByMemberId(memberId);

    return 1;
  }
  public List<OrderGetRes> findAllOrder(int memberId) {
    return orderMapper.findAllByMemberIdOrderByDesc(memberId);
  }
  public OrderDetailGetRes findDetail(OrderDetailGetReq req) {
    OrderDetailGetRes result = orderMapper.findByOrderIdAndMemberId(req);
    log.info("result:{}", result);
    return result;
  }
}
