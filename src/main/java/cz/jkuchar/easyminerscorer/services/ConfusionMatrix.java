package cz.jkuchar.easyminerscorer.services;

import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Confusion Matrix
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Component
@Scope("prototype")
public class ConfusionMatrix {
	
	private List<String> labels;
	private int[][] matrix;
	
	private ConfusionMatrix(){
		super();		
	}
	
	// https://github.com/haifengl/smile/blob/master/Smile/src/main/java/smile/validation/ConfusionMatrix.java
	
	public static ConfusionMatrix build(List<Object> truth, List<Object> prediction) {
		if(truth==null || prediction==null) {
			throw new IllegalArgumentException("truth or prediction is null");
		}
		
		ConfusionMatrix cm = new ConfusionMatrix();
		cm.labels = new LinkedList<String>();
		for(Object lab:truth){
			if(!cm.labels.contains(String.valueOf(lab))){
				cm.labels.add(String.valueOf(lab));
			}
		}
		for(Object lab:prediction){
			if(!cm.labels.contains(String.valueOf(lab))){
				cm.labels.add(String.valueOf(lab));
			}
		}
		if(cm.labels.size()==1 && cm.labels.get(0).equals("null")){
			cm.matrix = new int[0][0];
			return cm;
		}
		cm.matrix = new int[cm.labels.size()][cm.labels.size()];
		
		for(int i = 0; i < truth.size(); i++){			
			cm.matrix[cm.labels.indexOf(String.valueOf(truth.get(i)))][cm.labels.indexOf(String.valueOf(prediction.get(i)))] += 1;
		}
		
		return cm;
	}

	@ApiModelProperty(value="List of labels in CM (null for NA values)", required = true)
	public List<String> getLabels() {
		return labels;
	}

	@ApiModelProperty(value="2D array representing CM", required = true)
	public int[][] getMatrix() {
		return matrix;
	}

	@Override
	public String toString() {
		return "ConfusionMatrix [labels=" + labels + ", matrix="
				+ Arrays.deepToString(matrix) + "]";
	}	

}
