package com.myzh.sharding;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myzh.sharding.mapper.CompanyMapper;
import com.myzh.sharding.mapper.OrderMapper;
import com.myzh.sharding.mapper.UserInfoMapper;
import com.myzh.sharding.model.Company;
import com.myzh.sharding.model.Order;
import com.myzh.sharding.model.UserInfo;
import com.myzh.sharding.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * ShardingTableTests
 * 
 * @author lance
 * @since 2019.3.1 23:17
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingTableTests {
	private ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private UserInfoService userInfoService;

	@Before
	public void setUp() throws Exception {
		// hintManager.setDatabaseShardingValue("ds_0");
		// hintManager.addDatabaseShardingValue("t_user_info_0", 1);
	}

	// @Test
	@Ignore
	public void save() {
		BigInteger companyId = BigInteger.valueOf(2000);
		userInfoMapper.save(createUser(companyId, 0));
	}

	// @Test
	@Ignore
	public void saveOrder() {
		Order order = Order.builder().orderName("122").createTime(new Date()).build();
		orderMapper.save(order);
	}

	// @Test
	public void batchSaveOrder() throws ParseException {
		Date now = new Date();
		Date nextDate = DateUtils.parseDate("2020", "YYYY");
		List<CompletableFuture<Void>> futures = IntStream.range(0, 10).mapToObj(j -> CompletableFuture.runAsync(() -> {
			List<Order> list = IntStream.range(0, 500)
					.mapToObj(i -> Order.builder().orderName("Order." + i).createTime(nextDate).build())
					.collect(Collectors.toList());
			orderMapper.batchSave(list);
		}, service)).collect(Collectors.toList());

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[] {})).join();
	}

	@Test
	// @Ignore
	public void list() {
		BigInteger companyId = BigInteger.valueOf(3000);
		// Map<String, Object>map = Maps.newHashMap("companyId", companyId);
		Map<String, Object> map = new HashMap<>();
		int i = userInfoMapper.findCount(map);
		System.out.println("list ===========  " + i);
	}

	@Test
	// @Ignore
	public void batchSave() {
		try {
			userInfoService.batchInsertAndfind();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// hintManager.close();
		}
	}

	// @Test
	@Ignore
	public void saveCompany() {
		Company company = Company.builder().companyId(new BigInteger(RandomStringUtils.randomNumeric(4)))
				.companyName("33").address("222").createTime(new Date()).build();

		companyMapper.save(company);
	}

	private UserInfo createUser(BigInteger companyId, int index) {
		return UserInfo.builder().account("Account." + index).companyId(companyId)
				.password(RandomStringUtils.randomAlphabetic(8)).userName("Name." + index).build();
	}
}
