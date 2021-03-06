package com.lottery.ticket.phase.worker.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.phase.worker.AbstractPhaseCreate;
/**
 * 天津快10 20141224001 2014-12-24 09:05:00
 * 
 */
@Service
public class Tjk10PhaseCreate extends AbstractPhaseCreate{

	@Override
	public LotteryPhase creatNextPhase(LotteryPhase last) {
		String lastPhase = last.getPhase();
		String numStr = lastPhase.substring(8);
		
		String newPhase = null;
		Date endtime = null;
		if ("084".equals(numStr)) {
			String dateStr = lastPhase.substring(0, 8);
			Date date = DateUtil.parse("yyyyMMdd", dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			newPhase = DateUtil.format("yyyyMMdd", calendar.getTime());
			endtime = DateUtil.parse("yyyyMMdd HH:mm", newPhase + " " + "09:05");
			newPhase += "001";
		} else {
			int num = Integer.parseInt(numStr);
			String newIssueNum = DateUtil.fillZero(num + 1, 3);
			newPhase = lastPhase.substring(0, 8) + newIssueNum;
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(last.getEndTicketTime());
			calendar.add(Calendar.MINUTE, 10);
			endtime = calendar.getTime();
		}
		LotteryPhase lotteryPhase = new LotteryPhase();
		lotteryPhase.setLotteryType(last.getLotteryType());
		lotteryPhase.setPhase(newPhase);
		lotteryPhase.setStartSaleTime(last.getEndTicketTime());
		lotteryPhase.setEndTicketTime(endtime);
		lotteryPhase.setEndSaleTime(endtime);
		lotteryPhase.setHemaiEndTime(endtime);
		lotteryPhase.setDrawTime(endtime);
		
		updateCreatePhase(lotteryPhase);
		return lotteryPhase;
	}

	@Override
	@PostConstruct
	protected void init() {
		map.put(LotteryType.TJKL10, this);
	}
}
