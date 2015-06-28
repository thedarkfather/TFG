package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Result;
import repositories.ResultRepository;

@Service
public class ResultService {
	
	@Autowired
	private ResultRepository resultRepository;
	
	public void save(Result result){
		Assert.notNull(result);
		resultRepository.save(result);
	}

}
