package com.pulbatte.pulbatte.post.service;

import com.pulbatte.pulbatte.comment.dto.CommentResponseDto;
import com.pulbatte.pulbatte.comment.entity.Comment;
import com.pulbatte.pulbatte.comment.repository.CommentRepository;
import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.S3Uploader;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.post.dto.PostFavResponseDto;
import com.pulbatte.pulbatte.post.dto.PostRequestDto;
import com.pulbatte.pulbatte.post.dto.PostResponseDto;
import com.pulbatte.pulbatte.post.entity.Post;
import com.pulbatte.pulbatte.post.entity.PostLike;
import com.pulbatte.pulbatte.post.repository.LikeRepository;
import com.pulbatte.pulbatte.post.repository.PostRepository;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final S3Uploader s3Uploader;

    //게시글 생성
    public PostResponseDto createPost(PostRequestDto requestDto, User user, MultipartFile multipartFile) throws IOException {
        String image = null;
        if (multipartFile != null &&!multipartFile.isEmpty()) {                                     // 이미지 파일이 존재 할 경우            image = s3Uploader.upload(multipartFile, "static");     // s3이미지 업로드
            image = s3Uploader.upload(multipartFile, "post");                             // s3이미지 업로드
        }else{
            image = "";
        }
        Post post = postRepository.save(new Post(requestDto, user, image));
        return new PostResponseDto(post);
    }
    //게시글 전체 출력 페이징 처리
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getListPosts(Pageable pageable) {
        Page<Post> postList = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        Page<PostResponseDto> pageList = postList.map(
                post -> new PostResponseDto(
                        post,
                        (long) post.getPostLike().size(),
                        (long) post.getCommentList().size(),
                        post.getImage()
                ));
        return pageList;                                         // 페이징 처리
    }
    // 게시글 태그별 출력 페이징 처리
    public  Page<PostResponseDto> getTagListPosts(String tag , Pageable pageable){
        Page<Post> postPage = postRepository.findAllByTagOrderByCreatedAtDesc(tag,pageable);    // 입력받은 tag 와 같은 게시글 찾기
        Page<PostResponseDto> pageList = postPage.map(
                post -> new PostResponseDto(
                        post,
                        (long) post.getPostLike().size(),
                        (long) post.getCommentList().size(),
                        post.getImage()
                ));
        List<PostResponseDto> postResponseDto = new ArrayList<>();
        String image = null;
        for (Post post : postPage) {
            Long commentCnt = commentRepository.countByPostId(post.getId());                    // 댓글 수
            Long likeCnt = likeRepository.likeCnt(post.getId());                                // 좋아요 수
            if(post.getImage().isEmpty()){
                image = "https://d3usc6dqsfeh3v.cloudfront.net/post/noimage.png";
            }else {
                image = post.getImage();
            }
            postResponseDto.add(new PostResponseDto(post,likeCnt,commentCnt,image));
        }
        return pageList;
    }
    // 인기 게시글 출력
    public List<PostFavResponseDto> getPopularListPosts(){
        List<Post> postList = postRepository.findAll();
        List<PostFavResponseDto> postFavResponseDto = new ArrayList<>();
        Map<Long,LocalDateTime> likeList = new HashMap<>();                                             // 게시글 id와 인기게시글이 된 시간을 담을 hashMap 리스트
        for(Post post : postList){
            Long likeCnt = likeRepository.likeCnt(post.getId());                                        // 모든 게시글의 좋아요 수 체크
            if(likeCnt>3){                                                                              // 좋아요 수가 일정 수 이상이 되면
                likeList.put(post.getId(),post.getFavLikeTime());                                       // like 리스트에 게시글 id와 인기게시글이 된 시간 저장
            }
        }
        List<Map.Entry<Long,LocalDateTime>> sortLikeList = new LinkedList<>(likeList.entrySet());       // 리스트 정렬을 위해 리스트를 Entry 로 감쌈
        Collections.sort(sortLikeList, new Comparator<Map.Entry<Long, LocalDateTime>>() {               // 리스트를 인기게시글이 된 시간으로부터 내림차순 정렬 (최근에 인기 게시글이 됐으면 맨 앞)
            @Override
            public int compare(Map.Entry<Long, LocalDateTime> o1, Map.Entry<Long, LocalDateTime> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        String image = null;
        if(sortLikeList.size()<5){                                                                      // 리스트의 크기가 5보다 작을 때 (인기글의 초대 수 5)
            for (int i=0; i<sortLikeList.size(); i++) {
                Long postId = sortLikeList.get(i).getKey();                                             // 좋아요가 일정 수가 넘은 post 의 아이디 저장
                Post post = postRepository.findById(postId).orElseThrow(
                        () -> new CustomException(ErrorCode.NO_POST_FOUND)
                );                              // 저장한 아이디로 게시글 찾기
                if(post.getImage().isEmpty()){
                    image = "https://d3usc6dqsfeh3v.cloudfront.net/post/noimage.png";
                }else {
                    image = post.getImage();
                }
                postFavResponseDto.add(new PostFavResponseDto(post,image));                                   // 게시글 출력
            }
        }else {
            for (int i = 0; i < 5; i++) {                                                               // 게시글의 사이즈가 5보다 클 때 인기게시글이 된 시간으로 정렬 된 리스트에서 1~5번 째 까지만 출력
                Long postId = sortLikeList.get(i).getKey();                                             // 좋아요가 일정 수가 넘은 post 의 아이디 저장
                Post post = postRepository.findById(postId).orElseThrow(
                        () -> new CustomException(ErrorCode.NO_POST_FOUND)
                );                              // 저장한 아이디로 게시글 찾기
                if(post.getImage().isEmpty()){
                    image = "https://d3usc6dqsfeh3v.cloudfront.net/post/noimage.png";
                }else {
                    image = post.getImage();
                }
                postFavResponseDto.add(new PostFavResponseDto(post,image));                                   // 게시글 출력
            }
        }
       return postFavResponseDto;
    }
    //게시글 상세 조회(User용)
    @Transactional
    public PostResponseDto getPostUser(Long id,User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NO_POST_FOUND)
        );
        String profileImage = post.getUser().getProfileImage();                                  // 게시글 작성자 프로필 사진
        Long commentCnt = commentRepository.countByPostId(post.getId());                         // 댓글 수
        Long likeCnt = likeRepository.likeCnt(post.getId());                                     // 좋아요 수
        String image = post.getImage();                                                          // 이미지 url
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();                     // 댓글 리스트
        for (Comment comment : post.getCommentList()) {
            List<CommentResponseDto> childCommentList = new ArrayList<>();
            if(comment.getParent()==null){                                                       //부모 댓글이 없을 경우
                for (Comment childComment : comment.getReplyList()){                              //자식 댓글 리스트의 데이터를 childComment에 저장
                    if (id.equals(childComment.getPost().getId())) {                             //childComment의 id와 받아온 id가 일치할 경우(선택 게시글 저장)
                        childCommentList.add(new CommentResponseDto(childComment));              //저장된 자식댓글을 리스트에 저장
                    }
                }
                commentResponseDtoList.add(new CommentResponseDto(comment,childCommentList));
            }
        }
        Boolean likeStatus = false;
        if(likeRepository.findByPostIdAndUserId(post.getId(),user.getId()).isPresent()){
            likeStatus = true;
        }
        return new PostResponseDto(post, commentResponseDtoList, image, likeCnt,commentCnt,profileImage,likeStatus);
    }
    //게시글 상세 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPostGuest(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NO_POST_FOUND)
        );
        String profileImage = post.getUser().getProfileImage();                                  // 게시글 작성자 프로필 사진
        Long commentCnt = commentRepository.countByPostId(post.getId());                         // 댓글 수
        Long likeCnt = likeRepository.likeCnt(post.getId());                                     // 좋아요 수
        String image = post.getImage();                                                          // 이미지 url
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();                     // 댓글 리스트
        for (Comment comment : post.getCommentList()) {
            List<CommentResponseDto> childCommentList = new ArrayList<>();
            if(comment.getParent()==null){                                                       //부모 댓글이 없을 경우
                for (Comment childComment : comment.getReplyList()){                              //자식 댓글 리스트의 데이터를 childComment에 저장
                    if (id.equals(childComment.getPost().getId())) {                             //childComment의 id와 받아온 id가 일치할 경우(선택 게시글 저장)
                        childCommentList.add(new CommentResponseDto(childComment));              //저장된 자식댓글을 리스트에 저장
                    }
                }
                commentResponseDtoList.add(new CommentResponseDto(comment,childCommentList));
            }
        }
        return new PostResponseDto(post, commentResponseDtoList, image, likeCnt,commentCnt,profileImage);
    }
    //게시글 수정
    @Transactional
    public PostResponseDto updatePost(User user, Long id, PostRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        Post post;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {                                    // admin 계정일 경우
            post = postRepository.findById(id).orElseThrow(                                 // 입력받은 id와 같은 데이터 수정
                    () -> new CustomException(ErrorCode.NO_POST_FOUND)                     // 없으면 에러 출력
            );
        } else {                                                                            // 일반 user 계정일 경우
            post = postRepository.findByIdAndNickname(id, user.getNickname()).orElseThrow(  // 추가 검증
                    () -> new CustomException(ErrorCode.NO_POST_FOUND)
            );
        }
        post.update(requestDto);
        List<CommentResponseDto> commentList = new ArrayList<>();                           // 댓글 리스트
        for (Comment comment : post.getCommentList()) {
            commentList.add(new CommentResponseDto(comment));
        }
        String image = null;
        if (!multipartFile.isEmpty()) {                                                     // 사진이 수정된 경우
            image = (s3Uploader.upload(multipartFile, "post"));                   // 새로들어온 이미지 s3 저장
            Post posts = postRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_POST_FOUND)
            );
            s3Uploader.delete(posts.getImage(), "post");                          // 이전 이미지 파일 삭제
            posts.update(image);
        }
        return new PostResponseDto(post, commentList, image);
    }
    //게시글 삭제
    @Transactional
    public void deletePost(Long id, User user) {
        Post post;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {                                    // admin 계정일 경우
            post = postRepository.findById(id).orElseThrow(                                 // 입력받은 id와 같은 데이터 삭제
                    () -> new CustomException(ErrorCode.NO_POST_FOUND)                     // 없으면 에러 출력
            );
        } else {                                                                            // 일반 user 계정일 경우
            post = postRepository.findByIdAndNickname(id, user.getNickname()).orElseThrow(  // 추가 검증
                    () -> new CustomException(ErrorCode.NO_POST_FOUND)
            );
        }
        Post posts = postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NO_POST_FOUND)
        );
        s3Uploader.delete(posts.getImage(), "post");                              // 입력받은 아이디와 같은 이미지 삭제
        postRepository.delete(post);
    }
    //게시글 좋아요, 좋아요 취소
    @Transactional
    public MsgResponseDto postLike(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_POST_FOUND)
        );
        if (likeRepository.findByPostIdAndUserId(postId, user.getId()).isEmpty()) { // postLike 에 값이 있는지 확인
            likeRepository.save(new PostLike(post, user));                          // 없으면 저장
            if(likeRepository.likeCnt(postId)>3){
                LocalDateTime favLikeTime = LocalDateTime.now();
                post.updateFaveLikeTime(favLikeTime);
                System.out.println(favLikeTime);
            }
            return new MsgResponseDto(SuccessCode.LIKE);
        } else {
            likeRepository.deleteByPostIdAndUserId(post.getId(), user.getId());     // 있으면 삭제
            return new MsgResponseDto(SuccessCode.CANCEL_LIKE);
        }
    }
}
