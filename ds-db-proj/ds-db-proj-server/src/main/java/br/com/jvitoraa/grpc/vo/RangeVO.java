package br.com.jvitoraa.grpc.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import br.com.jvitoraa.grpc.dto.RangeDto;
import lombok.Data;

@Data
public class RangeVO {

	private static final String MOVE_RIGHT = "RIGHT";
	private static final String MOVE_LEFT = "LEFT";

	public RangeVO(Integer nValue, Integer mValue, Integer serverN) {

		this.serverN = serverN;

		Integer baseMinVal = NumberUtils.INTEGER_ZERO;
		Integer baseMaxVal = NumberUtils.INTEGER_ZERO;

		List<RangeDto> ranges = new ArrayList<RangeDto>();
		for (Integer i = NumberUtils.INTEGER_ZERO; i < nValue; i++) {

			if (i == NumberUtils.INTEGER_ZERO) {
				baseMaxVal = (int) Math.pow(2, mValue) - NumberUtils.INTEGER_ONE;
			} else {
				baseMaxVal = baseMinVal - NumberUtils.INTEGER_ONE;
			}
			baseMinVal = (int) ((int) baseMaxVal - Math.floor(Math.pow(2, mValue) / nValue) + NumberUtils.INTEGER_ONE);

			Range<Integer> range = Range.between(baseMinVal, baseMaxVal);
			RangeDto rangeDto = new RangeDto(i, range);
			ranges.add(rangeDto);
		}

		this.listOfRanges = ranges;

	}

	private List<RangeDto> listOfRanges;
	private Integer serverN;

	public String moveLeftOrRight(Integer number) {

		RangeDto destinationRange = listOfRanges.stream().filter(r -> r.getRange().contains(number)).findFirst().get();
	
		Iterator<RangeDto> it = Iterables.cycle(listOfRanges).iterator();
		Integer rightSteps = null;
		
		while(it.hasNext()) {
			RangeDto serverRange = (RangeDto) it.next();
			Integer insideN = serverRange.getServerN();
			
			if (insideN == this.serverN) {
				rightSteps = NumberUtils.INTEGER_ZERO;
			} else if (Objects.nonNull(rightSteps)) {
				rightSteps++;
				if (insideN == destinationRange.getServerN()) {
					break;
				}
			}
		}
		
		Iterator<RangeDto> itReversed = Iterables.cycle(Lists.reverse(listOfRanges)).iterator();
		Integer leftSteps = null;
		
		while(itReversed.hasNext()) {
			RangeDto serverRange = (RangeDto) itReversed.next();
			Integer insideN = serverRange.getServerN();
			
			if (insideN == this.serverN) {
				leftSteps = NumberUtils.INTEGER_ZERO;
			} else if (Objects.nonNull(leftSteps)) {
				leftSteps++;
				if (insideN == destinationRange.getServerN()) {
					break;
				}
			}
		}
		
		if (leftSteps < rightSteps) {
			return MOVE_LEFT;
		} else {
			return MOVE_RIGHT;
		}
	} 
	

}
