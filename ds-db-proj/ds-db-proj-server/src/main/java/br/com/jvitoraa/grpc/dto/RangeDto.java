package br.com.jvitoraa.grpc.dto;

import org.apache.commons.lang3.Range;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RangeDto {
	
	private Integer serverN;
	
	private Range<Integer> range;
	
}
