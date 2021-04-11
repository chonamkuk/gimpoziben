package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.AttachEntity;
import kr.co.kimpoziben.dto.SearchDto;
import kr.co.kimpoziben.test.domain.entity.AsEntity;
import kr.co.kimpoziben.test.domain.repository.AsRepository;
import kr.co.kimpoziben.test.dto.AsDto;
import kr.co.kimpoziben.util.AES256Util;
import kr.co.kimpoziben.util.SearchSpec;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ShopService {
    private AttachService attachService;

}
