package com.wora.gotYou.services.implementation;

import com.wora.gotYou.dtos.request.CreateRequestDto;
import com.wora.gotYou.dtos.request.UpdateRequestDto;
import com.wora.gotYou.dtos.request.RequestDto;
import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.Request;
import com.wora.gotYou.entities.Student;
import com.wora.gotYou.entities.User;
import com.wora.gotYou.entities.enums.RequestStatus;
import com.wora.gotYou.exceptions.NoDataFoundException;
import com.wora.gotYou.mappers.RequestMapper;
import com.wora.gotYou.repositories.RequestRepository;
import com.wora.gotYou.services.interfaces.RequestServiceInter;
import com.wora.gotYou.services.interfaces.StudentServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestServiceInter {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserServiceImpl userService;
    private final StudentServiceInter studentService;

    @Override
    public RequestDto save(CreateRequestDto dto) {
        Request request = requestMapper.toEntity(dto);
        request.setStatus(RequestStatus.WAITING);
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDTO(savedRequest);
    }

    @Override
    public RequestDto update(UpdateRequestDto dto, Long id) {
        Request existingRequest = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
//        requestMapper.updateRequestFromDto(dto, existingRequest);
        Request updatedRequest = requestRepository.save(existingRequest);
        return requestMapper.toDTO(updatedRequest);
    }

    public RequestDto getRequestById(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));
        return requestMapper.toDTO(request);
    }

    public List<RequestDto> getRequestsByStudentId() {
        UserDto userDto = userService.findByUserName();
        User user = studentService.getStudentById(userDto.getId());

//        if (!(user instanceof Student student)) {
//            throw new NoDataFoundException("The user is not a student and has no requests.");
//        }
        List<RequestDto> requests = student.getRequests().stream()
                .map(requestMapper::toDTO)
                .toList();

        if (requests.isEmpty()) {
            throw new NoDataFoundException("No Requests found for this Student");
        }

        return requests;
    }



    @Override
    public List<RequestDto> findAll() {
        return requestRepository.findAll()
                .stream()
                .map(requestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        requestRepository.deleteById(id);
    }
}