package com.myzh.sharding;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
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

import com.google.common.collect.Maps;
import com.myzh.sharding.entities.Company;
import com.myzh.sharding.entities.Order;
import com.myzh.sharding.entities.UserInfo;
import com.myzh.sharding.mapper.OrderMapper;
import com.myzh.sharding.service.CompanyService;
import com.myzh.sharding.service.UserInfoService;

import io.shardingsphere.api.HintManager;
import lombok.extern.slf4j.Slf4j;

/**
 * ShardingTableTests
 * 
 * @author ruqing
 * @since 2019.3.1 23:17
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingTableTests {
	private ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	@Autowired
	private CompanyService companyService;
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

	// @Test
	// @Ignore
	public void list() {
		// BigInteger provinceId = BigInteger.valueOf(3000);
		// Map<String, Object>map = Maps.newHashMap("provinceId", provinceId);

		int i = userInfoService.findCountForHintTest();
		System.out.println("test  cnt   ===========  " + i);
	}

	// @Test
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
	// @Ignore
	public void saveCompany() {
		try {
			Company company = Company.builder().companyId(new BigInteger(RandomStringUtils.randomNumeric(4)))
					.companyName("000").address("000").createTime(new Date()).provinceId(new BigInteger("0")).build();

			companyService.save(company);

			company = Company.builder().companyId(new BigInteger(RandomStringUtils.randomNumeric(4))).companyName("111")
					.address("111").createTime(new Date()).provinceId(new BigInteger("1")).build();

			companyService.save(company);

			company = Company.builder().companyId(new BigInteger(RandomStringUtils.randomNumeric(4))).companyName("222")
					.address("222").createTime(new Date()).provinceId(new BigInteger("2")).build();

			companyService.save(company);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// @Ignore
	public void listCompany() {
		HintManager hintManager = HintManager.getInstance();
		hintManager.setMasterRouteOnly();

		Map<String, Object> params = Maps.newHashMap();
		params.put("provinceId", new BigInteger("0"));
		List<Company> list = companyService.findAll(params);
		log.info("listCompany  0 ===========  " + list);
		hintManager.close();

		hintManager = HintManager.getInstance();
		hintManager.setMasterRouteOnly();
		params = Maps.newHashMap();
		params.put("provinceId", new BigInteger("1"));
		list = companyService.findAll(params);
		log.info("listCompany 1 =========== " + list);
		hintManager.close();

		hintManager = HintManager.getInstance();
		hintManager.setMasterRouteOnly();
		params = Maps.newHashMap();
		params.put("provinceId", new BigInteger("2"));

		list = companyService.findAll(params);
		log.info("listCompany 2 =========== " + list);
		hintManager.close();

	}

	private UserInfo createUser(BigInteger provinceId, int index) {
		return UserInfo.builder().account("Account." + index).provinceId(provinceId)
				.password(RandomStringUtils.randomAlphabetic(8)).userName("Name." + index).build();
	}
}
