import http from 'k6/http';
import {sleep} from 'k6';

export const options = {
    // 부하를 생성하는 step의 모음이 stage다.
    stages: [
        {duration: '10m', target: 6000}, // 10분에 걸쳐서 가상의 유저 수가 6000에 도달하도록 설정
    ],
}

export default function () {
    http.get('http://13.124.155.220/boards'); // 여기로 요청을 보냄
    sleep(1); // 그리고 1초 쉰다.
}