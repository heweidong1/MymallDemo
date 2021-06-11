package com.macro.mall.tiny.service.Impl;

import com.macro.mall.tiny.common.utils.JwtTokenUtil;
import com.macro.mall.tiny.mbg.mapper.UmsAdminRoleRelationDao;
import com.macro.mall.tiny.mbg.mapper.UmsAdminMapper;
import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsAdminExample;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private UmsAdminMapper adminMapper;

    @Resource
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;



    /**
     * 根据用户名获取后台管理员
     */
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(example);
        if(umsAdmins!=null&&umsAdmins.size()>0)
        {
            return umsAdmins.get(0);
        }
        return null;
    }

    /**
     * 注册功能
     */
    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam)
    {
        UmsAdmin umsAdmin =new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam,umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(example);
        if(umsAdmins.size()>0)
        {
            return null;
        }
        //将密码进行加密的操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password)
    {
        String token = null;
        try
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password, userDetails.getPassword()))
            {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generteToken(userDetails);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return token;
    }

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return umsAdminRoleRelationDao.getPermissionList(adminId);
    }
}
