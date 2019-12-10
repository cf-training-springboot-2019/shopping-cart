package com.training.springboot.shoppingcart.controller;

import com.training.springboot.shoppingcart.entity.model.Cart;
import com.training.springboot.shoppingcart.entity.request.CreateCartRequest;
import com.training.springboot.shoppingcart.entity.response.*;
import com.training.springboot.shoppingcart.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE_BASE_URL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * @JavaDoc ModelMapper is a mapping tool easily configurable to accommodate most application defined entities check
     * some configuration example at: http://modelmapper.org/user-manual/
     */
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<CreateCartResponse> createCart(@RequestBody @Valid CreateCartRequest request) {
        return new ResponseEntity<>(mapper.map(cartService.save(mapper.map(request, Cart.class)), CreateCartResponse.class),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<GetCartResponse>> getCart(@PathVariable("id") Long id) {
        Cart cart = cartService.get(id);
        GetCartResponse cartResponse = mapper.map(cart, GetCartResponse.class);
        cartResponse.setTotal(cartService.calculateCartTotal(cart));
        EntityModel<GetCartResponse> response = new EntityModel<>(cartResponse,
                linkTo(methodOn(CartController.class).getCart(id)).withSelfRel()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable("id") Long id, @RequestBody @Valid UpdateCartRequest cart) {
        cart.setCartUid(id);
        return new ResponseEntity<>(mapper.map(cartService.update(mapper.map(cart, Cart.class)), UpdateCartResponse.class),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") Long id) {
        cartService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Page<GetCartResponse>> listCarts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<Cart> cartPage = cartService.list(page, size);
        return new ResponseEntity<>(new PageImpl<>(
                cartService.list(page, size).stream().map(c -> {
                            GetCartResponse response = mapper.map(c, GetCartResponse.class);
                            response.setTotal(cartService.calculateCartTotal(c));
                            return response;
                        }
                )
                        .collect(Collectors.toList()), cartPage.getPageable(), cartPage.getTotalElements()), HttpStatus.OK);
    }

    @GetMapping("/{cart-uid}/items")
    public ResponseEntity<CollectionModel<EntityModel<GetCartItemResponse>>> listCartItems(
            @PathVariable("cart-uid") Long cartUid) {
        List<EntityModel<GetCartItemResponse>> entityModels = cartService.listCartItems(cartUid).stream().map(c ->
                new EntityModel<>(mapper.map(c, GetCartItemResponse.class),
                        linkTo(methodOn(CartController.class).getCartItem(cartUid, c.getItemUid())).withRel("Get item"),
                        linkTo(methodOn(CartController.class).deleteItem(c.getItemUid())).withRel("Decrease item qty"),
                        new Link(String.join("/", Arrays.asList(ITEM_STORAGE_BASE_URL, String.valueOf(c.getItemUid()))), "details"))
        ).collect(Collectors.toList());
        return new ResponseEntity<>(new CollectionModel<>(entityModels), HttpStatus.OK);
    }

    @GetMapping("/{cart-uid}/items/{item-uid}")
    public ResponseEntity<GetCartItemResponse> getCartItem(@PathVariable("cart-uid") Long cartUid,
                                                           @PathVariable("item-uid") Long itemUid) {
        return new ResponseEntity<>(mapper.map(cartService.getCartItem(cartUid, itemUid), GetCartItemResponse.class),
                HttpStatus.OK);
    }

    @PutMapping("/{id}/items")
    public ResponseEntity<HttpStatus> addCartItem(@PathVariable("id") Long cartUid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{cart-uid}/items/{item-uid}")
    public ResponseEntity<GetCartItemResponse> removeCartItem(@PathVariable("cart-uid") Long cartUid,
                                                              @PathVariable("item-uid") Long cartItemUid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
