package kr.co.wikibook.gallery.account;

import kr.co.wikibook.gallery.account.model.AccountLoginReq;
import kr.co.wikibook.gallery.account.model.AccountLoginRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {
  AccountLoginRes findByLoginIdAndLoginPw(AccountLoginReq req);
}
