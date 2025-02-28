import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    // 부하를 생성하는 단계(stages)를 설정
    stages: [
        // 10분에 걸쳐 vus(virtual users, 가상 유저수)가 50에 도달하도록 설정
        { duration: '10m', target: 50 }
    ],
};

export default function () {
    let random = Math.random();
    // 100명 중 5명의 비율로 게시글을 작성
    if (random < 0.05) {
        const data = { title: '제목', content: '내용' };
        http.post('http://wise-alb-2013782369.ap-northeast-2.elb.amazonaws.com/boards', JSON.stringify(data), {
            headers: { 'Content-Type': 'application/json' },
        });

        // 100명 중 95명의 비율로 게시글을 조회
    } else {
        http.get('http://wise-alb-2013782369.ap-northeast-2.elb.amazonaws.com/boards');
    }

    // 1초 휴식
    sleep(1);
}