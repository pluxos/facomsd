package br.com.jvitoraa.grpc.vo;

import org.apache.commons.lang3.math.NumberUtils;

import lombok.Data;

@Data
public class RangeVO {

	public RangeVO(Integer nValue, Integer mValue, Integer index) {

		Integer baseMinVal = NumberUtils.INTEGER_ZERO;
		Integer baseMaxVal = NumberUtils.INTEGER_ZERO;

		for (Integer i = NumberUtils.INTEGER_ZERO; i < nValue; i++) {

			if (i == NumberUtils.INTEGER_ZERO) {
				baseMaxVal = (int) Math.pow(2, mValue) - NumberUtils.INTEGER_ONE;
			} else {
				baseMaxVal = baseMinVal - NumberUtils.INTEGER_ONE;
			}
			baseMinVal = (int) ((int) baseMaxVal - Math.floor(Math.pow(2, mValue) / nValue) + NumberUtils.INTEGER_ONE);

			if (i == index) {
				break;
			}
		}

		Integer leftBaseIndex = null;
		Integer rightBaseIndex = null;

		if (index == NumberUtils.INTEGER_ZERO) {
			// 4 <- 0 -> 1
			leftBaseIndex = nValue;
			rightBaseIndex = index + NumberUtils.INTEGER_ONE;
		} else if (index == nValue) {
			// 3 <- 4 -> 0
			leftBaseIndex = index + NumberUtils.INTEGER_MINUS_ONE;
			rightBaseIndex = NumberUtils.INTEGER_ZERO;
		} else {
			// 2 <- 3 -> 4
			leftBaseIndex = index + NumberUtils.INTEGER_MINUS_ONE;
			rightBaseIndex = index + NumberUtils.INTEGER_ONE;
		}

		this.calcAdjacentRange(nValue, mValue, leftBaseIndex, true);
		this.calcAdjacentRange(nValue, mValue, rightBaseIndex, false);

		if (baseMinVal < NumberUtils.INTEGER_ZERO) {
			this.minVal = NumberUtils.INTEGER_ZERO;
		} else {
			this.minVal = baseMinVal;
		}
		this.maxVal = baseMaxVal;
	}

	private void calcAdjacentRange(Integer nValue, Integer mValue, Integer baseIndex, Boolean rightOrLeft) {
		Integer adjBaseMinVal = NumberUtils.INTEGER_ZERO;
		Integer adjBaseMaxVal = NumberUtils.INTEGER_ZERO;

		for (Integer i = NumberUtils.INTEGER_ZERO; i < nValue; i++) {

			if (i == NumberUtils.INTEGER_ZERO) {
				adjBaseMaxVal = (int) Math.pow(2, mValue) - NumberUtils.INTEGER_ONE;
			} else {
				adjBaseMaxVal = adjBaseMinVal - NumberUtils.INTEGER_ONE;
			}
			adjBaseMinVal = (int) ((int) adjBaseMaxVal - Math.floor(Math.pow(2, mValue) / nValue)
					+ NumberUtils.INTEGER_ONE);

			if (i == baseIndex) {
				break;
			}
		}

		if (rightOrLeft) {
			this.leftMaxVal = adjBaseMaxVal;
			if (adjBaseMinVal < NumberUtils.INTEGER_ZERO) {
				adjBaseMinVal = NumberUtils.INTEGER_ZERO;
			} else {
				this.leftMinVal = adjBaseMinVal;
			}
		} else {
			this.rightMaxVal = adjBaseMaxVal;
			if (adjBaseMinVal < NumberUtils.INTEGER_ZERO) {
				adjBaseMinVal = NumberUtils.INTEGER_ZERO;
			} else {
				this.rightMinVal = adjBaseMinVal;
			}
		}

	}

	private Integer minVal;
	private Integer maxVal;

	private Integer leftMinVal;
	private Integer leftMaxVal;

	private Integer rightMinVal;
	private Integer rightMaxVal;

}
