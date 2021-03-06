package br.com.six2six.fixturefactory.function.impl;

import java.util.Random;

import br.com.six2six.fixturefactory.function.AtomicFunction;

public class UniqueRandomFunction implements AtomicFunction{

	private Object[] dataset;
	
	private int nextValueIndex = 0;
	
	public UniqueRandomFunction(int minValue, int maxValue) {
		if(minValue >= maxValue) {
			throw new IllegalArgumentException("maxValue cannot be greater than minValue.");
		}
		this.dataset = this.initIntegerDataset(minValue, maxValue);
		this.shuffleDataset();
	}
	
	public UniqueRandomFunction(Object[] dataset) {
		if(dataset.length == 0) {
			throw new IllegalArgumentException("provided dataset has no elements.");
		}
		this.dataset = dataset;
		this.shuffleDataset();
	}
	
	public UniqueRandomFunction(Class<? extends Enum<?>> clazz) {
		if(clazz.getEnumConstants().length == 0) {
			throw new IllegalArgumentException("Enum has no values.");
		}
		this.dataset = clazz.getEnumConstants();
		this.shuffleDataset();
	}
	
	private Object[] initIntegerDataset(int minValue, int maxValue) {
		Integer[] dataset = new Integer[maxValue - minValue + 1];
		int currValue = minValue;
		for(int i = 0; i < dataset.length; i++) {
			dataset[i] = currValue;
			currValue ++;
		}
		
		return dataset;
	}
	
	private void shuffleDataset() {
		Random random = new Random();
		for(int shufflePosition = 0, iterator = dataset.length - 1; iterator > 0; iterator--) {
			shufflePosition = random.nextInt(iterator);
			Object temp = dataset[iterator];
			dataset[iterator] = dataset[shufflePosition];
			dataset[shufflePosition] = temp;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		
		if(this.nextValueIndex >= this.dataset.length) {
			this.nextValueIndex = 0; 
		}
		
		Object nextValue = this.dataset[this.nextValueIndex];
		this.nextValueIndex ++;
		return (T)nextValue;
		
	}
}