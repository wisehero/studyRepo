package kr.co.shortenurlservice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.shortenurlservice.domain.LackOfShortenUrlKeyException;
import kr.co.shortenurlservice.domain.NotFoundShortenUrlException;
import kr.co.shortenurlservice.domain.ShortenUrl;
import kr.co.shortenurlservice.domain.ShortenUrlRepository;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateRequestDto;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateResponseDto;
import kr.co.shortenurlservice.presentation.ShortenUrlInformationDto;

@Service
@Transactional(readOnly = true)
public class SimpleShortenUrlService {

	private final ShortenUrlRepository shortenUrlRepository;

	@Autowired
	SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
		this.shortenUrlRepository = shortenUrlRepository;
	}

	// 단축 URL 생성하는 비즈니스 로직
	@Transactional(readOnly = false)
	public ShortenUrlCreateResponseDto generateShortenUrl(ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) {
		String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl(); // 요청으로 들어온 원본 URL
		String shortenUrlKey = getUniqueShortenUrlKey(); // 단축 URL 키 생성

		// 단축 URL 생성
		ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
		shortenUrlRepository.saveShortenUrl(shortenUrl); // 단축 URL 저장

		return new ShortenUrlCreateResponseDto(shortenUrl);
	}

	@Transactional(readOnly = false)
	public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
		// 단축 URL 키로 단축 URL 조회
		ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

		// 단축 URL이 없으면 예외 발생
		if (null == shortenUrl)
			throw new NotFoundShortenUrlException();

		// 단축 URL의 리다이렉트 횟수 증가
		shortenUrl.increaseRedirectCount();
		shortenUrlRepository.saveShortenUrl(shortenUrl);

		return shortenUrl.getOriginalUrl();
	}

	// 단순히 단축 URL 정보 조회하는 비즈니스 로직
	public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
		ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

		if (null == shortenUrl)
			throw new NotFoundShortenUrlException();

		return new ShortenUrlInformationDto(shortenUrl);
	}

	private String getUniqueShortenUrlKey() {
		final int MAX_RETRY_COUNT = 5;
		int count = 0;

		while (count++ < MAX_RETRY_COUNT) {
			String shortenUrlKey = ShortenUrl.generateShortenUrlKey();
			ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

			if (null == shortenUrl)
				return shortenUrlKey;
		}

		throw new LackOfShortenUrlKeyException();
	}

}
