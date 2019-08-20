insert into resume (uuid, full_name) values
('1d6de108-a895-4bfc-8c56-056a9e081bc0','Name1'),
('acb9afce-b27c-4b76-9b10-27df76f97814','Name2'),
('0ace84a7-1652-4b85-9108-6162f52b0cc9','Name3'),
('8e97f92f-c24a-4648-93f5-a8e9f4cf9438','Name4');

insert into contact(resume_uuid, type, value) VALUES
('1d6de108-a895-4bfc-8c56-056a9e081bc0','PHONE','123456'),
('1d6de108-a895-4bfc-8c56-056a9e081bc0','SKYPE','skype'),
('1d6de108-a895-4bfc-8c56-056a9e081bc0','EMAIL','mail'),
('0ace84a7-1652-4b85-9108-6162f52b0cc9','PHONE','654321'),
('0ace84a7-1652-4b85-9108-6162f52b0cc9','EMAIL','mail3');