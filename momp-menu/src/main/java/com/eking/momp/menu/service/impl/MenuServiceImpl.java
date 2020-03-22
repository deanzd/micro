package com.eking.momp.menu.service.impl;

import com.eking.momp.menu.client.ModelFeignClient;
import com.eking.momp.menu.client.OrgFeignClient;
import com.eking.momp.menu.dao.MenuDao;
import com.eking.momp.menu.dto.MenuDTO;
import com.eking.momp.menu.param.UserParam;
import com.eking.momp.menu.po.MenuPO;
import com.eking.momp.menu.service.MenuService;
import com.eking.momp.menu.to.DimensionTO;
import com.eking.momp.mybatis.AbstractService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

@Service
public class MenuServiceImpl extends AbstractService<MenuDao, MenuPO> implements MenuService {
    @Autowired
    private ModelFeignClient modelFeignClient;
    @Autowired
    private OrgFeignClient orgFeignClient;

    @Override
    public List<MenuDTO> list() {
//		UserContext userContext = UserContextHoder.getUserContext();
//		List<Integer> permissionIds = userContext.getPermissionIds();
        //		checkPermission(menuDtos, permissionIds);
        return genChildMenus(null);
    }

    @SuppressWarnings("unused")
    private void checkPermission(List<MenuDTO> menuDtos, List<Integer> permissionIds) {
        menuDtos.forEach(menu -> {
            if (permissionIds.contains(menu.getId())) {
                if (!CollectionUtils.isEmpty(menu.getChildren())) {
                    checkPermission(menu.getChildren(), permissionIds);
                }
            } else {
                menuDtos.remove(menu);
            }
        });
    }

    private List<MenuDTO> genChildMenus(MenuPO parent) {
        List<MenuDTO> dtos = new ArrayList<>();
        // 根据parent_id
        super.lambdaQuery()
                .isNull(parent == null, MenuPO::getParentId)
                .eq(parent != null, MenuPO::getParentId, parent.getId())
                .orderByAsc(MenuPO::getShowOrder).list()
                .forEach(menuPO -> {
                    MenuDTO menuDto = new MenuDTO(menuPO);
                    List<MenuDTO> children = genChildMenus(menuPO);
                    menuDto.setChildren(children);
                    dtos.add(menuDto);
                });
        // dynamic
        if (parent != null && parent.getDynamicChildrenCode() != null) {
            List<MenuDTO> dynamicDtos = genDynamicChildMenus(parent.getPath(), parent.getDynamicChildrenCode());
            dtos.addAll(dynamicDtos);
        }
        return dtos;
    }

    private List<MenuDTO> genDynamicChildMenus(String path, String code) {
        if ("dimension".equals(code)) {
            List<MenuDTO> children = new ArrayList<>();
            List<DimensionTO> dimensionTOs = modelFeignClient.listDimensions();
            for (DimensionTO dimension : dimensionTOs) {
                children.add(new MenuDTO(dimension.getName(), path + "/" + dimension.getId(), null));
            }
            return children;
        } else if ("layer".equals(code)) {
            List<MenuDTO> children = new ArrayList<>();
            modelFeignClient.listLayers().forEach(layerTO -> {
                MenuDTO layerChild = new MenuDTO(layerTO.getName(), path + "/layer/" + layerTO.getId(), null);
                List<MenuDTO> modelChildren = new ArrayList<>();
                modelFeignClient.listModels(layerTO.getId()).forEach(modelTO -> {
                    MenuDTO modelChild = new MenuDTO(modelTO.getName(),
                            layerChild.getPath() + "/model/" + modelTO.getId() + "/" + modelTO.getCode() + "/0", null);
                    modelChildren.add(modelChild);
                });
                layerChild.setChildren(modelChildren);
                children.add(layerChild);
            });
            return children;
        }
        return Collections.emptyList();
    }

    @GlobalTransactional
    @Override
    @Transactional
    public void testSeata() {
        MenuPO menuPO = new MenuPO();
        menuPO.setName("testSeat");
        menuPO.setPath("//");
        menuPO.setParentId(new Random().nextInt());
        super.saveObj(menuPO);
        UserParam user = new UserParam();
        user.setPassword("1");
        user.setUsername("testSeata");
        user.setRoleId(1);
        orgFeignClient.saveUser(user);
        System.out.println(1/0);
    }
}
