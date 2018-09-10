package com.FileManagerX.BasicEnums;

public enum CMDType {

	BaseCommand,
	BaseReply,
	Unsupport,
	Test,
	CloseServer,
	CloseConnection,
	Restart,
	Operator,
	
	NewChannel,
	
	Open,
	Input,
	Output,
	Thumbnail,
	PrintScreen,
	
	RegisterMachine,
	RegisterUser,
	
	LoginConnection,
	LoginServer,
	LoginUser,
	LoginMachine,
	LoginType,
	
	QueryConfigurations,
	QueryUnit,
	QueryUnits,
	QuerySize,
	
	UpdateUnits,
	UpdateUnit,
	
	RemoveUnits,
	RemoveUnit,
	;
	
	public com.FileManagerX.Interfaces.ICommand getCommand() {
		if(this.equals(CMDType.BaseCommand)) {
			return new com.FileManagerX.Commands.BaseCommand();
		}
		if(this.equals(CMDType.BaseReply)) {
			return new com.FileManagerX.Commands.BaseCommand();
		}
		if(this.equals(CMDType.Unsupport)) {
			return new com.FileManagerX.Commands.Unsupport();
		}
		if(this.equals(CMDType.Test)) {
			return new com.FileManagerX.Commands.Test();
		}
		
		if(this.equals(CMDType.NewChannel)) {
			return new com.FileManagerX.Commands.NewChannel();
		}
		
		if(this.equals(CMDType.Operator)) {
			return new com.FileManagerX.Commands.Operator();
		}
		if(this.equals(CMDType.Input)) {
			return new com.FileManagerX.Commands.Input();
		}
		if(this.equals(CMDType.Output)) {
			return new com.FileManagerX.Commands.Output();
		}
		
		if(this.equals(CMDType.RegisterMachine)) {
			return new com.FileManagerX.Commands.RegisterMachine();
		}
		if(this.equals(CMDType.RegisterUser)) {
			return new com.FileManagerX.Commands.RegisterUser();
		}
		
		if(this.equals(CMDType.LoginConnection)) {
			return new com.FileManagerX.Commands.LoginConnection();
		}
		if(this.equals(CMDType.LoginServer)) {
			return new com.FileManagerX.Commands.LoginServer();
		}
		if(this.equals(CMDType.LoginUser)) {
			return new com.FileManagerX.Commands.LoginUser();
		}
		if(this.equals(CMDType.LoginMachine)) {
			return new com.FileManagerX.Commands.LoginMachine();
		}
		if(this.equals(CMDType.LoginType)) {
			return new com.FileManagerX.Commands.LoginType();
		}
		
		if(this.equals(CMDType.QueryConfigurations)) {
			return new com.FileManagerX.Commands.QueryConfigurations();
		}
		if(this.equals(CMDType.QuerySize)) {
			return new com.FileManagerX.Commands.QuerySize();
		}
		if(this.equals(CMDType.QueryUnits)) {
			return new com.FileManagerX.Commands.QueryUnits();
		}
		if(this.equals(CMDType.QueryUnit)) {
			return new com.FileManagerX.Commands.QueryUnit();
		}
		
		if(this.equals(CMDType.RemoveUnits)) {
			return new com.FileManagerX.Commands.RemoveUnits();
		}
		if(this.equals(CMDType.RemoveUnit)) {
			return new com.FileManagerX.Commands.RemoveUnit();
		}
		
		if(this.equals(CMDType.UpdateUnits)) {
			return new com.FileManagerX.Commands.UpdateUnits();
		}
		if(this.equals(CMDType.UpdateUnit)) {
			return new com.FileManagerX.Commands.UpdateUnit();
		}
		
		return null;
	}
	public com.FileManagerX.Interfaces.IReply getReply() {
		if(this.equals(CMDType.BaseCommand)) {
			return new com.FileManagerX.Replies.BaseReply();
		}
		if(this.equals(CMDType.BaseReply)) {
			return new com.FileManagerX.Replies.BaseReply();
		}
		if(this.equals(CMDType.Unsupport)) {
			return new com.FileManagerX.Replies.Unsupport();
		}
		if(this.equals(CMDType.Test)) {
			return new com.FileManagerX.Replies.Test();
		}
		
		if(this.equals(CMDType.NewChannel)) {
			return new com.FileManagerX.Replies.NewChannel();
		}
		
		if(this.equals(CMDType.Operator)) {
			return new com.FileManagerX.Replies.Operator();
		}
		if(this.equals(CMDType.Input)) {
			return new com.FileManagerX.Replies.Input();
		}
		if(this.equals(CMDType.Output)) {
			return new com.FileManagerX.Replies.Output();
		}
		
		if(this.equals(CMDType.RegisterMachine)) {
			return new com.FileManagerX.Replies.RegisterMachine();
		}
		if(this.equals(CMDType.RegisterUser)) {
			return new com.FileManagerX.Replies.RegisterUser();
		}
		
		if(this.equals(CMDType.LoginConnection)) {
			return new com.FileManagerX.Replies.LoginConnection();
		}
		if(this.equals(CMDType.LoginServer)) {
			return new com.FileManagerX.Replies.LoginServer();
		}
		if(this.equals(CMDType.LoginUser)) {
			return new com.FileManagerX.Replies.LoginUser();
		}
		if(this.equals(CMDType.LoginMachine)) {
			return new com.FileManagerX.Replies.LoginMachine();
		}
		if(this.equals(CMDType.LoginType)) {
			return new com.FileManagerX.Replies.LoginType();
		}
		
		if(this.equals(CMDType.QueryConfigurations)) {
			return new com.FileManagerX.Replies.QueryConfigurations();
		}
		if(this.equals(CMDType.QuerySize)) {
			return new com.FileManagerX.Replies.QuerySize();
		}
		if(this.equals(CMDType.QueryUnits)) {
			return new com.FileManagerX.Replies.QueryUnits();
		}
		if(this.equals(CMDType.QueryUnit)) {
			return new com.FileManagerX.Replies.QueryUnit();
		}
		
		if(this.equals(CMDType.RemoveUnits)) {
			return new com.FileManagerX.Replies.RemoveUnits();
		}
		if(this.equals(CMDType.RemoveUnit)) {
			return new com.FileManagerX.Replies.RemoveUnit();
		}
		
		if(this.equals(CMDType.UpdateUnits)) {
			return new com.FileManagerX.Replies.UpdateUnits();
		}
		if(this.equals(CMDType.UpdateUnit)) {
			return new com.FileManagerX.Replies.UpdateUnit();
		}
		
		return null;
	}
	
}
