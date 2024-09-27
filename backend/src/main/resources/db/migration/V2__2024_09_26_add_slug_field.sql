alter table public.book_
    add column slug varchar unique;

alter table public.author_
    add column slug varchar unique;

alter table public.user_
    add column slug varchar unique;

alter table public.image_
    add column slug varchar unique;

alter table public.order_
    add column slug varchar unique;